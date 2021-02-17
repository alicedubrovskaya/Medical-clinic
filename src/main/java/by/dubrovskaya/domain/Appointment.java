package by.dubrovskaya.domain;

import by.dubrovskaya.domain.enumeration.Status;

import java.util.Date;
import java.util.Objects;

public class Appointment extends Entity {
    private Date time;
    private Status status;
    private boolean isApproved;
    private String complaints;
    private String medicalReport;
    private String recommendation;
    private String disease;
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

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        if (!super.equals(o)) return false;
        Appointment that = (Appointment) o;
        return isApproved == that.isApproved && Objects.equals(time, that.time) && status == that.status && Objects.equals(complaints, that.complaints) && Objects.equals(medicalReport, that.medicalReport) && Objects.equals(recommendation, that.recommendation) && Objects.equals(disease, that.disease) && Objects.equals(doctor, that.doctor) && Objects.equals(patient, that.patient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), time, status, isApproved, complaints, medicalReport, recommendation, disease, doctor, patient);
    }
}
