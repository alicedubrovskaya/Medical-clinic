package service.impl;

import dao.DoctorDao;
import dao.PatientDao;
import dao.UserDao;
import domain.Doctor;
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
        transaction.setWithoutAutoCommit();
        UserDao userDao = transaction.createUserDao();
        PatientDao patientDao = transaction.createPatientDao();

        try {
            if (patient.getId() == null) {
                User user = userDao.read(patient.getLogin(), PasswordEncryption.encrypt(patient.getPassword()));
                patient.setId(user.getId());
                patientDao.create(patient);
            } else {
                patientDao.update(patient);
            }
            transaction.commit();
        } catch (PersistentException e) {
            transaction.rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        //TODO
        PatientDao patientDao = transaction.createPatientDao();
        patientDao.delete(id);
    }

    @Override
    public List<Patient> findAll() throws PersistentException {
        transaction.setAutoCommit();
        PatientDao patientDao = transaction.createPatientDao();
        List<Patient> patients = patientDao.read();
        buildPatient(patients);
        return patients;
    }

    @Override
    public Patient findByEmail(String email) throws PersistentException {
        transaction.setAutoCommit();
        PatientDao patientDao = transaction.createPatientDao();
        Patient patient = patientDao.readByEmail(email);
        if (patient != null) {
            buildPatient(Collections.singletonList(patient));
        }
        return patient;
    }

    @Override
    public Patient findById(Integer id) throws PersistentException {
        transaction.setAutoCommit();
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
