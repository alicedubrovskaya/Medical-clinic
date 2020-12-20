package service.impl;

import dao.VacationDao;
import domain.Patient;
import domain.Vacation;
import exception.PersistentException;
import service.VacationService;

import java.util.Date;
import java.util.List;

public class VacationServiceImpl extends ServiceImpl implements VacationService {
    //TODO

    @Override
    public void save(Vacation vacation) throws PersistentException {

    }

    @Override
    public List<Vacation> findByTime(Date date) throws PersistentException {
        VacationDao vacationDao = transaction.createDao(VacationDao.class);
        return vacationDao.readByTime(date);
    }

    @Override
    public Vacation findById(Integer id) throws PersistentException {
        return null;
    }

    @Override
    public void delete(Integer id) throws PersistentException {

    }
}
