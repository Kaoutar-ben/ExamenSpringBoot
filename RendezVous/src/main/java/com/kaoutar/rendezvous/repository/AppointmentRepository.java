package com.kaoutar.rendezvous.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kaoutar.rendezvous.model.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPractitionerId(Long practitionerId);
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByAppointmentDateBetween(LocalDateTime start, LocalDateTime end);
}
