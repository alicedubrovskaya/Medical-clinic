package by.dubrovskaya.dao;

import by.dubrovskaya.domain.Doctor;
import by.dubrovskaya.domain.Vacation;
import by.dubrovskaya.exception.PersistentException;

import java.util.Date;
import java.util.List;

public interface VacationDao extends Dao<Vacation> {

    List<Vacation> read() throws PersistentException;

    List<Vacation> readByTime(Date date) throws PersistentException;

    Vacation readBySpecifiedDate(Date date) throws PersistentException;
}
