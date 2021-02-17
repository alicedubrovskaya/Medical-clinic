package by.dubrovskaya.dao;

import by.dubrovskaya.domain.Doctor;
import by.dubrovskaya.domain.User;
import by.dubrovskaya.exception.PersistentException;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DoctorDao extends Dao<Doctor> {
    List<Doctor> read() throws PersistentException;

    List<Doctor> readBySpecializationType(String specialization) throws PersistentException;

    Doctor readBySurnameAndName(String surname, String name) throws PersistentException;

    List<Doctor> readWithoutVacation(Date date) throws PersistentException;

    List<String> readSpecializations() throws PersistentException;

    Map<Integer, List<Doctor>> read(int offset, int noOfRecords) throws PersistentException;

}
