package com.waris.pms_sevice.service;
import com.waris.pms_sevice.dto.PatientDTO;
import com.waris.pms_sevice.entity.Patient;
import com.waris.pms_sevice.repository.MedicalRecordRepository;
import com.waris.pms_sevice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

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
                .orElse(null);
    }

    public Page<PatientDTO> getAllPatients(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name())
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Patient> patientPage = patientRepository.findAll(pageable);

        return patientPage.map(patient -> new PatientDTO(
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
        ));
    }

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

    public List<PatientDTO> findPatientsByName(String name) {
        return patientRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name,name);
    }
    public long getPatientCount() {
        return patientRepository.count();
    }

    public List<PatientDTO> getAllPatientsWithoutPagination() {
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

}
