package com.waris.pms_sevice.controller;

import com.waris.pms_sevice.dto.PatientWithRecordsDTO;
import com.waris.pms_sevice.entity.MedicalRecord;
import com.waris.pms_sevice.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medical-records")
@CrossOrigin(origins = "http://localhost:4200")

public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestParam Long patientId, @RequestBody MedicalRecord record) {
        try {
            MedicalRecord savedRecord = medicalRecordService.createMedicalRecord(patientId, record);
            return ResponseEntity.ok(savedRecord);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<List<MedicalRecord>> getRecordsByPatientId(@PathVariable Long patientId) {
        try {
            List<MedicalRecord> records = medicalRecordService.getRecordsByPatientId(patientId);
            return ResponseEntity.ok(records);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecord updatedRecord) {
        Optional<MedicalRecord> record = medicalRecordService.updateMedicalRecord(id, updatedRecord);
        return record.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long id) {
        if (medicalRecordService.deleteMedicalRecord(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/all-records")
    public ResponseEntity<List<PatientWithRecordsDTO>> getAllPatientsWithMedicalRecords() {
        List<PatientWithRecordsDTO> patientsWithRecords = medicalRecordService.getAllPatientsWithMedicalRecords();
        return ResponseEntity.ok(patientsWithRecords);
    }
}
