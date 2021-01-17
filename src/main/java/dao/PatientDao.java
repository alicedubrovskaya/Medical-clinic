package dao;

import domain.Doctor;
import domain.Patient;
import exception.PersistentException;

import java.util.List;

public interface PatientDao extends Dao<Patient> {
    List<Patient> read() throws PersistentException;

    Patient readByEmail(String email) throws PersistentException;

}
