package dao;

import domain.Doctor;
import exception.PersistentException;

import java.util.List;

public interface DoctorDao extends Dao<Doctor> {
    List<Doctor> readBySpecializationType(String specialization) throws PersistentException;
}
