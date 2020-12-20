package service.impl;

import dao.DoctorDao;
import dao.UserDao;
import domain.Doctor;
import domain.User;
import domain.enumeration.Role;
import exception.PersistentException;
import service.DoctorService;

import java.util.Collections;
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
        Doctor doctor =  doctorDao.read(id);
        if (doctor!= null){
            buildDoctor(Collections.singletonList(doctor));
        }
        return doctor;
    }

    @Override
    public List<Doctor> findBySpecializationType(String specialization) throws PersistentException {
        DoctorDao doctorDao = transaction.createDao(DoctorDao.class);
        List<Doctor> doctors =  doctorDao.readBySpecializationType(specialization);
        buildDoctor(doctors);
        return doctors;
    }

    @Override
    public List<Doctor> findAll() throws PersistentException {
        DoctorDao doctorDao = transaction.createDao(DoctorDao.class);
        List<Doctor> doctors =  doctorDao.read();
        buildDoctor(doctors);
        return doctors;
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        DoctorDao doctorDao = transaction.createDao(DoctorDao.class);
        doctorDao.delete(id);
    }

    private void buildDoctor(List<Doctor> doctors) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);

        for (Doctor doctor : doctors) {
            if (doctor.getId() != null) {
                User user = userDao.read(doctor.getId());
                if (user != null) {
                    doctor.setLogin(user.getLogin());
                    //TODO encoding
                    doctor.setPassword(user.getPassword());
                    doctor.setRole(Role.DOCTOR);
                }
            }
        }
    }
}
