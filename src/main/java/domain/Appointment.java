package domain;

import domain.enumeration.Status;

import java.sql.Date;

public class Appointment extends Entity {
    private Date time;
    private Status status;
    private boolean isApproved;
    private String complaints;
    private String medicalReport;
    private String recommendation;
    private Doctor doctor;
    private Patient patient;

    public Date getTime() {
        return time;
    }

    public Status getStatus() {
        return status;
    }

    public String getComplaints() {
        return complaints;
    }

    public String getMedicalReport() {
        return medicalReport;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    public void setMedicalReport(String medicalReport) {
        this.medicalReport = medicalReport;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
