package dao;

import domain.Patient;
import exception.PersistentException;

public interface PatientDao extends Dao<Patient> {
    Patient readByEmail(String email) throws PersistentException;

}
