package service;

import domain.Appointment;
import domain.Doctor;
import exception.PersistentException;
import service.exception.ServicePersistentException;

import java.util.Date;
import java.util.List;

public interface AppointmentService extends Service {
    void save(Appointment appointment) throws PersistentException;

    void save(Appointment appointment, String disease) throws ServicePersistentException;

    Appointment findById(Integer id) throws PersistentException;

    List<Appointment> findAll() throws PersistentException;

    List<Appointment> findByTime(Date date) throws PersistentException;

    Appointment findByTimeAndDoctor(Date date, Doctor doctor) throws PersistentException;

    List<Appointment> findByTimeAndSpecialization(Date date, String specialization) throws PersistentException;

    public List<Appointment> findByDateAndStatusAndDoctor(Date date, String status, Integer doctorId) throws PersistentException;

    Appointment findByPatientAndDisease(Integer patientId, String diseaseName) throws PersistentException;

    List<Appointment> findByPatient(Integer patientId) throws PersistentException;

    void delete(Integer id) throws PersistentException;

    void createAppointmentsForDoctors(Date date, int countOfDays) throws PersistentException;
}
