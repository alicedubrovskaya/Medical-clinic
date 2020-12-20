package service.impl;

import dao.PatientDao;
import dao.UserDao;
import domain.Patient;
import domain.User;
import domain.enumeration.Role;
import exception.PersistentException;
import service.PatientService;

import java.util.Collections;
import java.util.List;

public class PatientServiceImpl extends ServiceImpl implements PatientService {

    @Override
    public void save(Patient patient) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        PatientDao patientDao = transaction.createDao(PatientDao.class);

        if (patient.getId() == null) {
            User user = userDao.read(patient.getLogin(), patient.getPassword());
            patient.setId(user.getId());
            patientDao.create(patient);
        } else {
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
        Patient patient = patientDao.readByEmail(email);
        if (patient != null) {
            buildPatient(Collections.singletonList(patient));
        }
        return patient;
    }

    @Override
    public Patient findById(Integer id) throws PersistentException {
        PatientDao patientDao = transaction.createDao(PatientDao.class);
        Patient patient = patientDao.read(id);
        if (patient != null) {
            buildPatient(Collections.singletonList(patient));
        }
        return patient;
    }

    private void buildPatient(List<Patient> patients) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);

        for (Patient patient : patients) {
            if (patient.getId() != null) {
                User user = userDao.read(patient.getId());
                if (user != null) {
                    patient.setLogin(user.getLogin());
                    //TODO encoding
                    patient.setPassword(user.getPassword());
                    patient.setRole(Role.PATIENT);
                }
            }
        }
    }
}
