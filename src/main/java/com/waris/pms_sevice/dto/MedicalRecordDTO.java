package com.waris.pms_sevice.dto;


import java.time.LocalDateTime;

public class MedicalRecordDTO {
    private Long id;
    private String diagnosis;
    private String treatment;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long patientId;

    public MedicalRecordDTO(Long id, String diagnosis, String treatment, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.patientId = patientId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}

