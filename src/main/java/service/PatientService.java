package service;

import domain.Patient;
import exception.PersistentException;
import service.exception.ServicePersistentException;

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
