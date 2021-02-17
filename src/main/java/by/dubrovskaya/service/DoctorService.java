package by.dubrovskaya.service;

import by.dubrovskaya.domain.Doctor;
import by.dubrovskaya.domain.User;
import by.dubrovskaya.exception.ServicePersistentException;

import java.util.List;
import java.util.Map;

public interface DoctorService extends Service {
    void save(Doctor doctor) throws ServicePersistentException;

    Doctor findById(Integer id) throws ServicePersistentException;

    Doctor findBySurnameAndName(String surname, String name) throws ServicePersistentException;

    List<Doctor> findBySpecializationType(String specialization) throws ServicePersistentException;

    List<Doctor> findAll() throws ServicePersistentException;

    List<String> findAllSpecializations() throws ServicePersistentException;

    Map<Integer, List<Doctor>> find(int offset, int noOfRecords) throws ServicePersistentException;

    void delete(Integer id) throws ServicePersistentException;
}
