package service.impl;

import dao.PatientDao;
import dao.UserDao;
import domain.Patient;
import domain.User;
import domain.enumeration.Role;
import exception.PersistentException;
import service.PasswordEncryption;
import service.PatientService;

import java.util.Collections;
import java.util.List;

public class PatientServiceImpl extends ServiceImpl implements PatientService {

    @Override
    public void save(Patient patient) throws PersistentException {
        UserDao userDao = transaction.createUserDao();
        PatientDao patientDao = transaction.createPatientDao();

        if (patient.getId() == null) {
            User user = userDao.read(patient.getLogin(), PasswordEncryption.encrypt(patient.getPassword()));
            patient.setId(user.getId());
            patientDao.create(patient);
        } else {
            patientDao.update(patient);
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        PatientDao patientDao = transaction.createPatientDao();
        patientDao.delete(id);
    }

    @Override
    public Patient findByEmail(String email) throws PersistentException {
        PatientDao patientDao = transaction.createPatientDao();
        Patient patient = patientDao.readByEmail(email);
        if (patient != null) {
            buildPatient(Collections.singletonList(patient));
        }
        return patient;
    }

    @Override
    public Patient findById(Integer id) throws PersistentException {
        PatientDao patientDao = transaction.createPatientDao();
        Patient patient = patientDao.read(id);
        if (patient != null) {
            buildPatient(Collections.singletonList(patient));
        }
        return patient;
    }

    private void buildPatient(List<Patient> patients) throws PersistentException {
        UserDao userDao = transaction.createUserDao();

        for (Patient patient : patients) {
            if (patient.getId() != null) {
                User user = userDao.read(patient.getId());
                if (user != null) {
                    patient.setLogin(user.getLogin());
                    patient.setPassword(user.getPassword());
                    patient.setRole(Role.PATIENT);
                }
            }
        }
    }
}
