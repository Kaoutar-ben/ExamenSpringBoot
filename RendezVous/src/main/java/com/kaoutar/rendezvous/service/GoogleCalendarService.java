package com.kaoutar.rendezvous.service;

import java.io.FileInputStream;
import java.time.ZoneId;
import java.util.Collections;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.kaoutar.rendezvous.model.Appointment;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class GoogleCalendarService {
    
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String APPLICATION_NAME = "Service Rendez-vous";
    private static final String CALENDAR_ID = "primary";
    
    @Value("${google.calendar.credentials.path}")
    private String credentialsPath;
    
    private Calendar calendarService;
    
    @CircuitBreaker(name = "googleCalendar", fallbackMethod = "createEventFallback")
    @Retry(name = "googleCalendar")
    public String createEvent(Appointment appointment) {
        try {
            Calendar service = getCalendarService();
            
            Event event = new Event()
                .setSummary("Rendez-vous médical")
                .setDescription("Rendez-vous avec le praticien ID: " + appointment.getPractitionerId());

            DateTime startDateTime = new DateTime(
                appointment.getAppointmentDate()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()
            );
            
            DateTime endDateTime = new DateTime(
                appointment.getEndTime()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()
            );

            EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone(TimeZone.getDefault().getID());
            
            EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone(TimeZone.getDefault().getID());

            event.setStart(start);
            event.setEnd(end);
            
            Event createdEvent = service.events()
                .insert(CALENDAR_ID, event)
                .execute();
                
            return createdEvent.getId();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de l'événement Google Calendar", e);
        }
    }
    
    public String createEventFallback(Appointment appointment, Exception e) {
        // Retourner un ID temporaire en cas d'échec
        return "TEMP_" + System.currentTimeMillis();
    }
    
    private synchronized Calendar getCalendarService() throws Exception {
        if (calendarService == null) {
            Credential credential = GoogleCredential.fromStream(new FileInputStream(credentialsPath))
                .createScoped(Collections.singleton(CalendarScopes.CALENDAR));

            calendarService = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
        }
        return calendarService;
    }

    @CircuitBreaker(name = "googleCalendar", fallbackMethod = "deleteEventFallback")
    @Retry(name = "googleCalendar")
    public void deleteEvent(String eventId) {
        try {
            Calendar service = getCalendarService();
            service.events().delete(CALENDAR_ID, eventId).execute();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de l'événement Google Calendar", e);
        }
    }

    public void deleteEventFallback(String eventId, Exception e) {
        // Log l'échec de la suppression pour une tentative ultérieure
        System.err.println("Échec de la suppression de l'événement " + eventId + ": " + e.getMessage());
    }
} 