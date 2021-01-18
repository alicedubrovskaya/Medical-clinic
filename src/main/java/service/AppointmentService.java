package service;

import domain.Appointment;
import domain.Doctor;
import exception.PersistentException;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AppointmentService extends Service {
    void save(Appointment appointment) throws PersistentException;

    void saveGenerated(Appointment appointment) throws PersistentException;

    Appointment findById(Integer id) throws PersistentException;

    List<Appointment> findAll() throws PersistentException;

    List<Appointment> findByTime(Date date) throws PersistentException;

    Appointment findByTimeAndDoctor(Date date, Doctor doctor) throws PersistentException;

    List<Appointment> findByTimeAndSpecialization(Date date, String specialization) throws PersistentException;

    Appointment findByPatientAndDisease(Integer patientId, String diseaseName) throws PersistentException;

    List<Appointment> findByPatient(Integer patientId) throws PersistentException;

    void delete(Integer id) throws PersistentException;

    List<Appointment> createAppointments(Date date, Doctor doctor) throws PersistentException;

    void createAppointmentsForDoctors(Date date, int countOfDays) throws PersistentException;
}
