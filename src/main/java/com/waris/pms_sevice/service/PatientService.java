package com.waris.pms_sevice.service;



import com.waris.pms_sevice.dto.PatientDTO;
import com.waris.pms_sevice.entity.Patient;
import com.waris.pms_sevice.exception.PatientNotFoundException;
import com.waris.pms_sevice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    // Register a new patient
    public Patient registerPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    // Retrieve patient by ID
    public PatientDTO getPatientById(Long id) {
        return patientRepository.findById(id)
                .map(patient -> new PatientDTO(
                        patient.getId(),
                        patient.getFirstName(),
                        patient.getLastName(),
                        patient.getDateOfBirth(),
                        patient.getGender(),
                        patient.getPhone(),
                        patient.getEmail(),
                        patient.getStreet(),
                        patient.getCity(),
                        patient.getState(),
                        patient.getZipCode()
                ))
                .orElse(null); // return null or throw an exception if not found
    }

    // Retrieve all patients
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream()
                .map(patient -> new PatientDTO(
                        patient.getId(),
                        patient.getFirstName(),
                        patient.getLastName(),
                        patient.getDateOfBirth(),
                        patient.getGender(),
                        patient.getPhone(),
                        patient.getEmail(),
                        patient.getStreet(),
                        patient.getCity(),
                        patient.getState(),
                        patient.getZipCode()
                ))
                .collect(Collectors.toList());
    }

    // Update patient information
    public Patient updatePatient(Long id, Patient updatedPatient) {
        updatedPatient.setId(id);
        return patientRepository.save(updatedPatient);
    }


    public String deletePatient(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return "0";
        }else{
            patientRepository.deleteById(id);
            return "1";
        }
    }
}
