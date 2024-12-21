package com.kaoutar.dossiermedicalservice.Service;


import com.kaoutar.dossiermedicalservice.Model.MedicalRecord;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalRecordService {
    private static final List<MedicalRecord> medicalRecordsDb = new ArrayList<>();

    static {
        MedicalRecord record1 = new MedicalRecord();
        record1.setId(1);
        record1.setPatientId(1001);
        record1.setPracticienId("P001");
        record1.setContent("Consultation générale : Patient en bonne santé. Aucun traitement nécessaire.");

        MedicalRecord record2 = new MedicalRecord();
        record2.setId(2);
        record2.setPatientId(1002);
        record2.setPracticienId("P002");
        record2.setContent("Diagnostic de diabète de type 2. Prescription de metformine 500 mg deux fois par jour.");

        MedicalRecord record3 = new MedicalRecord();
        record3.setId(3);
        record3.setPatientId(1003);
        record3.setPracticienId("P003");
        record3.setContent("Suivi de grossesse : 3ème trimestre. Examen échographique effectué.");


        MedicalRecord record4 = new MedicalRecord();
        record4.setId(4);
        record4.setPatientId(1004);
        record4.setPracticienId("P004");
        record4.setContent("Traitement post-opératoire : Surveillance des points de suture après une intervention chirurgicale.");

        MedicalRecord record5 = new MedicalRecord();
        record5.setId(5);
        record5.setPatientId(1005);
        record5.setPracticienId("P005");
        record5.setContent("Détection d'une allergie sévère aux antibiotiques de la famille des pénicillines.");

    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordsDb;
    }

    @Retryable(
            value = {Exception.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    @HystrixCommand(fallbackMethod = "getMedicalRecordByIdFallback")
    public MedicalRecord creteMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordsDb.add(medicalRecord);
        return medicalRecord;
    }

    public MedicalRecord getMedicalRecordByIdFallback(int id) {
        return medicalRecordsDb.stream().filter(record -> record.getId() == id)
                .findFirst().orElseThrow(() -> new RuntimeException("Dossier avec l'id " + id + " n'existe pas"));
    }

    public MedicalRecord updateMedicalRecord(int id, MedicalRecord updatedRecord) {
        MedicalRecord existingRecord = medicalRecordsDb.stream()
                .filter(record -> record.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Dossier avec l'id " + id + " n'existe pas"));

        existingRecord.setPatientId(updatedRecord.getPatientId());
        existingRecord.setPracticienId(updatedRecord.getPracticienId());
        existingRecord.setContent(updatedRecord.getContent());

        return existingRecord;
    }

    public String deleteMedicalRecord(int id) {
        MedicalRecord recordToDelete = medicalRecordsDb.stream()
                .filter(record -> record.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Dossier avec l'id " + id + " n'existe pas"));

        medicalRecordsDb.remove(recordToDelete);
        return "Dossier médical supprimé avec succès.";
    }
}
