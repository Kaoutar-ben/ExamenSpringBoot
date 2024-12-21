package com.kaoutar.rendezvous.service;

import com.kaoutar.rendezvous.model.Appointment;
import com.kaoutar.rendezvous.repository.AppointmentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;
    private final GoogleCalendarService calendarService;

    public AppointmentService(AppointmentRepository repository, GoogleCalendarService calendarService) {
        this.repository = repository;
        this.calendarService = calendarService;
    }

    @Retryable(value = { RuntimeException.class }, maxAttempts = 3)
    @CircuitBreaker(name = "appointmentService", fallbackMethod = "fallbackCreateAppointment")
    public Appointment createAppointment(Appointment appointment) {
        appointment.setStatus("CONFIRMED");
        repository.save(appointment);

        calendarService.createEvent(
                "Rendez-vous avec le praticien",
                "Cabinet médical",
                appointment.getAppointmentDate(),
                appointment.getAppointmentDate().plusHours(1)
        );

        return appointment;
    }

    public List<Appointment> getAppointmentsForPatient(Long patientId) {
        return repository.findByPatientId(patientId);
    }

    public Appointment fallbackCreateAppointment(Appointment appointment, Throwable throwable) {
        appointment.setStatus("FAILED");
        return appointment;
    }
}
