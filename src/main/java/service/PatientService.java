package service;

import domain.Patient;
import exception.PersistentException;

public interface PatientService extends Service{
    void save(Patient patient) throws PersistentException;

    void delete(Integer id) throws PersistentException;

    Patient findByEmail(String email) throws PersistentException;

    Patient findById(Integer id) throws PersistentException;
}
