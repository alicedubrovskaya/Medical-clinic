package service.impl;

import dao.PatientDao;
import dao.UserDao;
import domain.Patient;
import domain.User;
import exception.PersistentException;
import service.PatientService;

public class PatientServiceImpl extends ServiceImpl implements PatientService {

    @Override
    public void save(Patient patient) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        PatientDao patientDao = transaction.createDao(PatientDao.class);

        if (patient.getId() == null) {
            User user = userDao.read(patient.getLogin(), patient.getPassword());
            patient.setId(user.getId());
            patientDao.create(patient);
        }
        else {
            patientDao.update(patient);
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        PatientDao patientDao = transaction.createDao(PatientDao.class);
        patientDao.delete(id);
    }

    @Override
    public Patient findByEmail(String email) throws PersistentException {
        PatientDao patientDao = transaction.createDao(PatientDao.class);
        return patientDao.readByEmail(email);
    }

    @Override
    public Patient findById(Integer id) throws PersistentException {
        PatientDao patientDao = transaction.createDao(PatientDao.class);
        return patientDao.read(id);
    }
}
