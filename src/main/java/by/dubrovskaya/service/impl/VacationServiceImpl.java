package by.dubrovskaya.service.impl;

import by.dubrovskaya.dao.DoctorDao;
import by.dubrovskaya.dao.VacationDao;
import by.dubrovskaya.domain.Doctor;
import by.dubrovskaya.domain.Vacation;
import by.dubrovskaya.exception.PersistentException;
import by.dubrovskaya.service.VacationService;
import by.dubrovskaya.exception.ServicePersistentException;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Vacation by.dubrovskaya.service
 */
public class VacationServiceImpl extends ServiceImpl implements VacationService {

    /**
     * Saves vacation
     *
     * @param vacation
     * @throws ServicePersistentException
     */
    @Override
    public void save(Vacation vacation) throws ServicePersistentException {
        VacationDao vacationDao = transaction.createVacationDao();
        try {
            if (vacation.getId() != null) {
                if (vacationDao.read(vacation.getId()) != null) {
                    vacationDao.update(vacation);
                } else vacationDao.create(vacation);
            } else {
                throw new ServicePersistentException("Vacation has no id (doctor)");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Finds vacations by time
     *
     * @param date
     * @return list of found vacations
     * @throws ServicePersistentException
     */
    @Override
    public List<Vacation> findByTime(Date date) throws ServicePersistentException {
        VacationDao vacationDao = transaction.createVacationDao();
        try {
            List<Vacation> vacations = vacationDao.readByTime(date);
            if (!vacations.isEmpty()) {
                return vacations;
            } else {
                throw new ServicePersistentException("Empty list of vacations");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Finds all vacations
     *
     * @return list of vacations
     * @throws ServicePersistentException
     */
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

    /**
     * Finds vacation by id
     *
     * @param id
     * @return found vacation
     * @throws ServicePersistentException
     */
    @Override
    public Vacation findById(Integer id) throws ServicePersistentException {
        VacationDao vacationDao = transaction.createVacationDao();
        try {
            Vacation vacation = vacationDao.read(id);
            if (vacation != null) {
                buildVacation(Collections.singletonList(vacation));
                return vacation;
            } else {
                throw new ServicePersistentException("Vacation wasn't found");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Deletes vacation by id
     *
     * @param id
     * @throws ServicePersistentException
     */
    @Override
    public void delete(Integer id) throws ServicePersistentException {
        VacationDao vacationDao = transaction.createVacationDao();
        try {
            vacationDao.delete(id);
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Fills missing fields of vacations
     *
     * @param vacations
     * @throws PersistentException
     */
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
