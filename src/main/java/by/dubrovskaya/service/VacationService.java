package by.dubrovskaya.service;

import by.dubrovskaya.domain.Vacation;
import by.dubrovskaya.exception.ServicePersistentException;

import java.util.Date;
import java.util.List;

public interface VacationService extends Service{
    void save(Vacation vacation) throws ServicePersistentException;

    List<Vacation> findByTime(Date date) throws ServicePersistentException;

    List<Vacation> findAll() throws ServicePersistentException;

    Vacation findById(Integer id) throws ServicePersistentException;

    void delete(Integer id) throws ServicePersistentException;
}
