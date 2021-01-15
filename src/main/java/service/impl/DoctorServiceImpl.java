package service.impl;

import dao.DoctorDao;
import dao.UserDao;
import domain.Doctor;
import domain.User;
import domain.enumeration.Role;
import exception.PersistentException;
import service.DoctorService;
import service.PasswordEncryption;

import java.util.Collections;
import java.util.List;

public class DoctorServiceImpl extends ServiceImpl implements DoctorService {
    @Override
    public void save(Doctor doctor) throws PersistentException {
        transaction.setWithoutAutoCommit();
        UserDao userDao = transaction.createUserDao();
        DoctorDao doctorDao = transaction.createDoctorDao();

        try {
            if (doctor.getId() == null) {
                User user = userDao.read(doctor.getLogin(), PasswordEncryption.encrypt(doctor.getPassword()));
                doctor.setId(user.getId());
            //    doctorDao.create(doctor);
            } else {
            //    doctorDao.update(doctor);
            }
            transaction.commit();
        } catch (PersistentException e) {
            transaction.rollback();
            //TODO service exception
            throw new PersistentException();
        }
    }

    @Override
    public Doctor findById(Integer id) throws PersistentException {
        transaction.setAutoCommit();
        DoctorDao doctorDao = transaction.createDoctorDao();
        Doctor doctor = doctorDao.read(id);
        if (doctor != null) {
            buildDoctor(Collections.singletonList(doctor));
        }
        return doctor;
    }

    @Override
    public List<Doctor> findBySpecializationType(String specialization) throws PersistentException {
        transaction.setAutoCommit();
        DoctorDao doctorDao = transaction.createDoctorDao();
        List<Doctor> doctors = doctorDao.readBySpecializationType(specialization);
        buildDoctor(doctors);
        return doctors;
    }

    @Override
    public List<Doctor> findAll() throws PersistentException {
        transaction.setAutoCommit();
        DoctorDao doctorDao = transaction.createDoctorDao();
        List<Doctor> doctors = doctorDao.read();
        buildDoctor(doctors);
        return doctors;
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        //TODO
        transaction.setAutoCommit();
        DoctorDao doctorDao = transaction.createDoctorDao();
        doctorDao.delete(id);
    }

    private void buildDoctor(List<Doctor> doctors) throws PersistentException {
        UserDao userDao = transaction.createUserDao();
        for (Doctor doctor : doctors) {
            if (doctor.getId() != null) {
                User user = userDao.read(doctor.getId());
                if (user != null) {
                    doctor.setLogin(user.getLogin());
                    doctor.setPassword(user.getPassword());
                    doctor.setRole(Role.DOCTOR);
                }
            }
        }
    }
}
