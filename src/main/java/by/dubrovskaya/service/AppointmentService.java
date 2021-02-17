package by.dubrovskaya.service;

import by.dubrovskaya.domain.Appointment;
import by.dubrovskaya.domain.Doctor;
import by.dubrovskaya.exception.ServicePersistentException;

import java.util.Date;
import java.util.List;

public interface AppointmentService extends Service {
    void save(Appointment appointment) throws ServicePersistentException;

    void save(Appointment appointment, String disease) throws ServicePersistentException;

    Appointment findById(Integer id) throws ServicePersistentException;

    List<Appointment> findAll() throws ServicePersistentException;

    List<Appointment> findByTime(Date date) throws ServicePersistentException;

    List<Appointment> findByTimeAndSpecialization(Date date, String specialization) throws ServicePersistentException;

    List<Appointment> findByDateAndStatusAndDoctor(Date date, String status, Integer doctorId) throws ServicePersistentException;

    List<Appointment> findByPatient(Integer patientId) throws ServicePersistentException;

    List<Appointment> findByDateAndStatus(Date date, String status) throws ServicePersistentException;

    List<Appointment> findByPatientAndStatus(Integer patientId, String status) throws ServicePersistentException;

    void delete(Integer id) throws ServicePersistentException;

    void createAppointmentsForDoctors(Date date, int countOfDays) throws ServicePersistentException;
}
