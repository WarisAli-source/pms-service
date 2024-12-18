package com.waris.pms_sevice.controller;

import com.waris.pms_sevice.dto.PatientDTO;
import com.waris.pms_sevice.entity.Patient;
import com.waris.pms_sevice.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<Patient> registerPatient(@RequestBody Patient patient) {
        Patient savedPatient = patientService.registerPatient(patient);
        return ResponseEntity.ok(savedPatient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Long id) {
        PatientDTO patientDTO = patientService.getPatientById(id);
        return patientDTO != null ? ResponseEntity.ok(patientDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Page<PatientDTO>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        Page<PatientDTO> patients = patientService.getAllPatients(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(patients);
    }
    @GetMapping("/allPatientsWithoutPagination")
    public ResponseEntity<List<PatientDTO>> getAllPatientsWithoutPagination() {
        List<PatientDTO> patients = patientService.getAllPatientsWithoutPagination();
        return ResponseEntity.ok(patients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        Patient updatedPatient = patientService.updatePatient(id, patient);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        String status = patientService.deletePatient(id);
        if(status.equalsIgnoreCase("1")){
            return ResponseEntity.ok("Success");
        }else{
            return ResponseEntity.ok("Fail");
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<PatientDTO>> searchPatients(@RequestParam String name) {
        List<PatientDTO> patients = patientService.findPatientsByName(name);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/patientCount")
    public ResponseEntity<Long> getPatientCount() {
        long count = patientService.getPatientCount();
        return ResponseEntity.ok(count);
    }
}
