package service;

import domain.Vacation;
import exception.PersistentException;
import service.exception.ServicePersistentException;

import java.util.Date;
import java.util.List;

public interface VacationService extends Service{
    void save(Vacation vacation) throws PersistentException;

    List<Vacation> findByTime(Date date) throws PersistentException;

    List<Vacation> findAll() throws ServicePersistentException;

    Vacation findById(Integer id) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
