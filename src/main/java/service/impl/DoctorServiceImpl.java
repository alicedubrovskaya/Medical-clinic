package service.impl;

import dao.DoctorDao;
import dao.UserDao;
import domain.Doctor;
import domain.User;
import exception.PersistentException;
import service.DoctorService;

import java.util.List;

public class DoctorServiceImpl extends ServiceImpl implements DoctorService {
    @Override
    public void save(Doctor doctor) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        DoctorDao doctorDao = transaction.createDao(DoctorDao.class);

        if (doctor.getId() == null) {
            User user = userDao.read(doctor.getLogin(), doctor.getPassword());
            doctor.setId(user.getId());
            doctorDao.create(doctor);
        } else {
            doctorDao.update(doctor);
        }
    }

    @Override
    public Doctor findById(Integer id) throws PersistentException {
        DoctorDao doctorDao = transaction.createDao(DoctorDao.class);
        return doctorDao.read(id);
    }

    @Override
    public List<Doctor> findBySpecializationType(String specialization) throws PersistentException {
        DoctorDao doctorDao = transaction.createDao(DoctorDao.class);
        return doctorDao.readBySpecializationType(specialization);
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        DoctorDao doctorDao = transaction.createDao(DoctorDao.class);
        doctorDao.delete(id);
    }
}
