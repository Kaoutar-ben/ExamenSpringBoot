package com.kaoutar.patientservice.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.kaoutar.patientservice.model.Patient;
import org.springframework.stereotype.Service;


@Service
public class PatientService {
    private final List<Patient> patients = new ArrayList<>();
    private Long nextId = 1L;

    public PatientService() {
        // Ajout de quelques patients de test
        patients.add(new Patient(nextId++, "Dupont", "Jean", LocalDate.of(1980, 5, 15), "jean.dupont@email.com"));
        patients.add(new Patient(nextId++, "Martin", "Sophie", LocalDate.of(1992, 8, 21), "sophie.martin@email.com"));
        patients.add(new Patient(nextId++, "Bernard", "Marie", LocalDate.of(1975, 3, 10), "marie.bernard@email.com"));
        patients.add(new Patient(nextId++, "Petit", "Pierre", LocalDate.of(1988, 12, 3), "pierre.petit@email.com"));
        patients.add(new Patient(nextId++, "Robert", "Anne", LocalDate.of(1995, 7, 25), "anne.robert@email.com"));
    }

    public List<Patient> getAllPatients() {
        return patients;
    }

    public Optional<Patient> getPatientById(Long id) {
        return patients.stream()
                .filter(patient -> patient.getId().equals(id))
                .findFirst();
    }

    public Patient savePatient(Patient patient) {
        if (patient.getId() == null) {
            patient.setId(nextId++);
        }
        patients.add(patient);
        return patient;
    }

    public void deletePatient(Long id) {
        patients.removeIf(patient -> patient.getId().equals(id));
    }

    public Patient updatePatient(Long id, Patient patient) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getId().equals(id)) {
                patient.setId(id);
                patients.set(i, patient);
                return patient;
            }
        }
        return null;
    }
}
