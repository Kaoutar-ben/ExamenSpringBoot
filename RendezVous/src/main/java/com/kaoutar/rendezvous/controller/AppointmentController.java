package com.kaoutar.rendezvous.controller;

import com.kaoutar.rendezvous.model.Appointment;
import com.kaoutar.rendezvous.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return service.createAppointment(appointment);
    }

    @GetMapping("/patient/{patientId}")
    public List<Appointment> getAppointments(@PathVariable Long patientId) {
        return service.getAppointmentsForPatient(patientId);
    }
}
