package com.waris.pms_sevice.repository;

import com.waris.pms_sevice.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}