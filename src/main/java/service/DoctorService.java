package service;

import domain.Doctor;
import domain.Patient;
import exception.PersistentException;
import service.exception.ServicePersistentException;

import java.util.List;

public interface DoctorService extends Service {
    void save(Doctor doctor) throws ServicePersistentException;

    Doctor findById(Integer id) throws ServicePersistentException;

    Doctor findBySurnameAndName(String surname, String name) throws ServicePersistentException;

    List<Doctor> findBySpecializationType(String specialization) throws ServicePersistentException;

    List<Doctor> findAll() throws ServicePersistentException;

    List<String> findAllSpecializations() throws ServicePersistentException;

    void delete(Integer id) throws ServicePersistentException;
}
