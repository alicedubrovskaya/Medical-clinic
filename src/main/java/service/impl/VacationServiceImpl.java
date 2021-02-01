package service.impl;

import dao.DoctorDao;
import dao.VacationDao;
import domain.Doctor;
import domain.Vacation;
import exception.PersistentException;
import service.VacationService;
import service.exception.ServicePersistentException;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class VacationServiceImpl extends ServiceImpl implements VacationService {

    @Override
    public void save(Vacation vacation) throws PersistentException {
        VacationDao vacationDao = transaction.createVacationDao();
        if (vacation.getId() != null) {
            if (vacationDao.read(vacation.getId()) != null) {
                vacationDao.update(vacation);
            } else vacationDao.create(vacation);
        } else {
            vacation.setId(vacationDao.create(vacation));
        }
    }

    @Override
    public List<Vacation> findByTime(Date date) throws PersistentException {
        VacationDao vacationDao = transaction.createVacationDao();
        return vacationDao.readByTime(date);
    }

    @Override
    public List<Vacation> findAll() throws ServicePersistentException {
        VacationDao vacationDao = transaction.createVacationDao();
        try {
            List<Vacation> vacations = vacationDao.read();
            if (!vacations.isEmpty()) {
                buildVacation(vacations);
                return vacations;
            } else {
                throw new ServicePersistentException("Empty list of vacations");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public Vacation findById(Integer id) throws PersistentException {
        VacationDao vacationDao = transaction.createVacationDao();
        Vacation vacation = vacationDao.read(id);
        if (vacation != null) {
            buildVacation(Collections.singletonList(vacation));
        }
        return vacation;
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        //TODO
        VacationDao vacationDao = transaction.createVacationDao();
        vacationDao.delete(id);
    }

    private void buildVacation(List<Vacation> vacations) throws PersistentException {
        DoctorDao doctorDao = transaction.createDoctorDao();
        for (Vacation vacation : vacations) {
            if (vacation.getId() != null) {
                Doctor doctor = doctorDao.read(vacation.getId());
                if (doctor != null) {
                    vacation.setDoctor(doctor);
                }
            }
        }
    }
}
