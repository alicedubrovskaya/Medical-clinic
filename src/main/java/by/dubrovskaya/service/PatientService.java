package by.dubrovskaya.service;

import by.dubrovskaya.domain.Patient;
import by.dubrovskaya.exception.ServicePersistentException;

import java.util.List;

public interface PatientService extends Service{
    void save(Patient patient) throws ServicePersistentException;

    void delete(Integer id) throws ServicePersistentException;

    List<Patient> findAll() throws ServicePersistentException;

    Patient findByEmail(String email) throws ServicePersistentException;

    Patient findById(Integer id) throws ServicePersistentException;

    List<String> findDiseases() throws ServicePersistentException;

    List<String> findDiseasesByPatient(Integer patientId) throws ServicePersistentException;

}