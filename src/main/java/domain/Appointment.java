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
    private String timetable;
    private MedicalCard medicalCard;
    private Doctor doctor;

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

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public MedicalCard getMedicalCard() {
        return medicalCard;
    }

    public Doctor getDoctor() {
        return doctor;
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

    public void setMedicalCard(MedicalCard medicalCard) {
        this.medicalCard = medicalCard;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
