package dao;

import domain.Doctor;
import exception.PersistentException;

import java.util.Date;
import java.util.List;

public interface DoctorDao extends Dao<Doctor> {
    List<Doctor> read() throws PersistentException;

    List<Doctor> readBySpecializationType(String specialization) throws PersistentException;

    List<Doctor> readWithoutVacation(Date date) throws PersistentException;
}
