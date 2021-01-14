package service.impl;

import dao.VacationDao;
import domain.Vacation;
import exception.PersistentException;
import service.VacationService;

import java.util.Date;
import java.util.List;

public class VacationServiceImpl extends ServiceImpl implements VacationService {

    @Override
    public void save(Vacation vacation) throws PersistentException {
        transaction.setAutoCommit();
        VacationDao vacationDao = transaction.createVacationDao();
        if (vacation.getId() != null) {
            vacationDao.update(vacation);
        } else {
            vacation.setId(vacationDao.create(vacation));
        }
    }

    @Override
    public List<Vacation> findByTime(Date date) throws PersistentException {
        transaction.setAutoCommit();
        VacationDao vacationDao = transaction.createVacationDao();
        return vacationDao.readByTime(date);
    }

    @Override
    public Vacation findById(Integer id) throws PersistentException {
        transaction.setAutoCommit();
        VacationDao vacationDao = transaction.createVacationDao();
        return vacationDao.read(id);
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        //TODO
        VacationDao vacationDao= transaction.createVacationDao();
        vacationDao.delete(id);
    }
}
