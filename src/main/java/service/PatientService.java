package service;

import domain.Doctor;
import domain.Patient;
import exception.PersistentException;

import java.util.List;

public interface PatientService extends Service{
    void save(Patient patient) throws PersistentException;

    void delete(Integer id) throws PersistentException;

    List<Patient> findAll() throws PersistentException;

    Patient findByEmail(String email) throws PersistentException;

    Patient findById(Integer id) throws PersistentException;
}
