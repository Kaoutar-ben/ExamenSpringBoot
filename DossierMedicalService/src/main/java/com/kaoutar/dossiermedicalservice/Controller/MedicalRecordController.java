package com.kaoutar.dossiermedicalservice.Controller;

import com.kaoutar.dossiermedicalservice.Model.MedicalRecord;
import com.kaoutar.dossiermedicalservice.Service.MedicalRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    // 1. Récupérer tous les dossiers médicaux
    @GetMapping
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {
        List<MedicalRecord> records = medicalRecordService.getAllMedicalRecords();
        return ResponseEntity.ok(records);
    }

    // 2. Créer un nouveau dossier médical
    @PostMapping("/create")
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        MedicalRecord createdRecord = medicalRecordService.creteMedicalRecord(medicalRecord);
        return ResponseEntity.ok(createdRecord);
    }

    // 3. Récupérer un dossier médical par ID
    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecord> getMedicalRecordById(@PathVariable int id) {
        MedicalRecord record = medicalRecordService.getMedicalRecordByIdFallback(id);
        return ResponseEntity.ok(record);
    }

    // 4. Mettre à jour un dossier médical
    @PutMapping("/update/{id}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(
            @PathVariable int id,
            @RequestBody MedicalRecord updatedRecord) {
        MedicalRecord record = medicalRecordService.updateMedicalRecord(id, updatedRecord);
        return ResponseEntity.ok(record);
    }

    // 5. Supprimer un dossier médical
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMedicalRecord(@PathVariable int id) {
        String result = medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.ok(result);
    }
}
