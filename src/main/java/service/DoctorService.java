package service;

import domain.Doctor;
import domain.Patient;
import exception.PersistentException;

import java.util.List;

public interface DoctorService extends Service {
    void save(Doctor doctor) throws PersistentException;

    Doctor findById(Integer id) throws PersistentException;

    List<Doctor> findBySpecializationType(String specialization) throws PersistentException;

    List<Doctor> findAll() throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
