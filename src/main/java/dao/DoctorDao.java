package dao;

import domain.Doctor;
import domain.Specialization;
import exception.PersistentException;

import java.util.List;

public interface DoctorDao extends Dao<Doctor> {
    List<Doctor> readBySpecialization(Specialization specialization) throws PersistentException;
}
