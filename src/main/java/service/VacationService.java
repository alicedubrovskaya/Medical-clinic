package service;

import domain.Doctor;
import domain.Patient;
import domain.Vacation;
import exception.PersistentException;

import java.util.Date;
import java.util.List;

public interface VacationService extends Service{
    void save(Vacation vacation) throws PersistentException;

    List<Vacation> findByTime(Date date) throws PersistentException;

    List<Vacation> findAll() throws PersistentException;

    Vacation findById(Integer id) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
