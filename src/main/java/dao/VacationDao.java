package dao;

import domain.Vacation;
import exception.PersistentException;

import java.util.Date;
import java.util.List;

public interface VacationDao extends Dao<Vacation> {
    List<Vacation> readByTime(Date date) throws PersistentException;
}
