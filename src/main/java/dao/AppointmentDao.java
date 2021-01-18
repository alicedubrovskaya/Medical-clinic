package dao;

import domain.Appointment;
import domain.Doctor;
import exception.PersistentException;


import java.util.Date;
import java.util.List;

public interface AppointmentDao extends Dao<Appointment> {
    List<Appointment> createAppointments(Date date, Doctor doctor) throws PersistentException;

    List<Appointment> readAll() throws PersistentException;

    List<Appointment> readByTime(Date date) throws PersistentException;

    List<Appointment> readByPatient(Integer patientId) throws PersistentException;

    Appointment readByTimeAndDoctor(Date date, Doctor doctor) throws PersistentException;

    List<Appointment> readByTimeAndSpecialization(Date date, String specialization) throws PersistentException;

    Appointment readByPatientAndDisease(Integer patientId, String diseaseName) throws PersistentException;
}
