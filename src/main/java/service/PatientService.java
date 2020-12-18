package service;

import domain.Patient;
import domain.User;
import exception.PersistentException;

public interface PatientService extends Service{
    void save(Patient patient) throws PersistentException;

    void delete(Integer id) throws PersistentException;

    Patient findByEmail(String email) throws PersistentException;

    Patient findById(String id) throws PersistentException;
}
