package com.waris.pms_sevice.service;

import com.waris.pms_sevice.dto.MedicalRecordDTO;
import com.waris.pms_sevice.dto.PatientWithRecordsDTO;
import com.waris.pms_sevice.entity.MedicalRecord;
import com.waris.pms_sevice.entity.Patient;
import com.waris.pms_sevice.repository.MedicalRecordRepository;
import com.waris.pms_sevice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PatientRepository patientRepository;

    public MedicalRecord createMedicalRecord(Long patientId, MedicalRecord medicalRecord) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (patient.isPresent()) {
            medicalRecord.setPatient(patient.get());
            medicalRecord.setCreatedAt(LocalDateTime.now());
            medicalRecord.setUpdatedAt(LocalDateTime.now());
            return medicalRecordRepository.save(medicalRecord);
        } else {
            throw new IllegalArgumentException("Patient not found");
        }
    }

    public List<MedicalRecord> getRecordsByPatientId(Long patientId) {
        if (patientRepository.existsById(patientId)) {
            return medicalRecordRepository.findByPatientId(patientId);
        } else {
            return new ArrayList<>();
        }
    }

    public Optional<MedicalRecord> updateMedicalRecord(Long id, MedicalRecord updatedRecord) {
        return medicalRecordRepository.findById(id).map(record -> {
            record.setDiagnosis(updatedRecord.getDiagnosis());
            record.setTreatment(updatedRecord.getTreatment());
            record.setNotes(updatedRecord.getNotes());
            record.setUpdatedAt(LocalDateTime.now());
            return medicalRecordRepository.save(record);
        });
    }

    public boolean deleteMedicalRecord(Long id) {
        if (medicalRecordRepository.existsById(id)) {
            medicalRecordRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<PatientWithRecordsDTO> getAllPatientsWithMedicalRecords() {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream().map(patient -> {
            List<MedicalRecordDTO> medicalRecords = patient.getMedicalRecords().stream()
                    .map(record -> new MedicalRecordDTO(
                            record.getId(),
                            record.getDiagnosis(),
                            record.getTreatment(),
                            record.getNotes(),
                            record.getCreatedAt(),
                            record.getUpdatedAt()
                    )).collect(Collectors.toList());

            return new PatientWithRecordsDTO(
                    patient.getId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getGender(),
                    patient.getPhone(),
                    patient.getEmail(),
                    medicalRecords
            );
        }).collect(Collectors.toList());
    }

    public Optional<MedicalRecord> getMedicalRecordsById(Long id) {
        return medicalRecordRepository.findById(id);
    }
}
