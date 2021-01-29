package dao;

import domain.Doctor;
import exception.PersistentException;

import java.util.Date;
import java.util.List;

public interface DoctorDao extends Dao<Doctor> {
    List<Doctor> read() throws PersistentException;

    List<Doctor> readBySpecializationType(String specialization) throws PersistentException;

    Doctor readBySurnameAndName(String surname, String name) throws PersistentException;

    List<Doctor> readWithoutVacation(Date date) throws PersistentException;

    List<String> readSpecializations() throws PersistentException;
}
