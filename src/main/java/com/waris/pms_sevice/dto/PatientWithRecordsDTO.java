package com.waris.pms_sevice.dto;

import java.util.List;

public class PatientWithRecordsDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String phone;
    private String email;
    private List<MedicalRecordDTO> medicalRecords;

    public PatientWithRecordsDTO(Long id, String firstName, String lastName, String gender, String phone, String email, List<MedicalRecordDTO> medicalRecords) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.medicalRecords = medicalRecords;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<MedicalRecordDTO> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<MedicalRecordDTO> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }
}
