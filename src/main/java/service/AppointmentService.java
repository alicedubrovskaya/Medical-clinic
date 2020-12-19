package service;

import domain.Appointment;
import domain.Doctor;
import exception.PersistentException;

import java.util.Date;
import java.util.List;

public interface AppointmentService extends Service {
    void save(Appointment appointment) throws PersistentException;

    void saveGenerated(Appointment appointment) throws PersistentException;

    Appointment findById(Integer id) throws PersistentException;

    List<Appointment> findByTime(Date date) throws PersistentException;

    Appointment findByTimeAndDoctor(Date date, Doctor doctor) throws PersistentException;

    Appointment findByPatientAndDisease(Integer patientId, String diseaseName) throws PersistentException;

    void delete(Integer id) throws PersistentException;

    List<Appointment> createAppointments(Date date, Doctor doctor) throws PersistentException;
}
