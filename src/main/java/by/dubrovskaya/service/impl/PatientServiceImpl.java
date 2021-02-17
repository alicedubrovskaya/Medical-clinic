package by.dubrovskaya.service.impl;

import by.dubrovskaya.dao.PatientDao;
import by.dubrovskaya.dao.UserDao;
import by.dubrovskaya.domain.Patient;
import by.dubrovskaya.domain.User;
import by.dubrovskaya.domain.enumeration.Role;
import by.dubrovskaya.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.dubrovskaya.service.util.PasswordEncryptionUtil;
import by.dubrovskaya.service.PatientService;
import by.dubrovskaya.exception.ServicePersistentException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Patient by.dubrovskaya.service
 */
public class PatientServiceImpl extends ServiceImpl implements PatientService {
    private static final Logger logger = LogManager.getLogger(PatientServiceImpl.class);

    /**
     * Saves patient
     *
     * @param patient
     * @throws ServicePersistentException
     */
    public void save(Patient patient) throws ServicePersistentException {
        transaction.setWithoutAutoCommit();
        UserDao userDao = transaction.createUserDao();
        PatientDao patientDao = transaction.createPatientDao();

        try {
            if (patient.getId() == null) {
                Integer userId = userDao.create(new User(patient.getLogin(),
                        PasswordEncryptionUtil.encrypt(patient.getPassword()), Role.PATIENT)
                );
                patient.setId(userId);
                patientDao.create(patient);
            } else {
                patientDao.update(patient);
            }
            transaction.commit();
        } catch (PersistentException e) {
            try {
                transaction.rollback();
            } catch (PersistentException ex) {
                logger.warn("Transaction can not be rollbacked:{} ", ex.getMessage());
            }
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Deletes patient by id
     *
     * @param id
     * @throws PersistentException
     */
    @Override
    public void delete(Integer id) throws ServicePersistentException {
        transaction.setWithoutAutoCommit();
        UserDao userDao = transaction.createUserDao();
        PatientDao patientDao = transaction.createPatientDao();
        try {
            patientDao.delete(id);
            userDao.delete(id);
            transaction.commit();
        } catch (PersistentException e) {
            try {
                transaction.rollback();
            } catch (PersistentException ex) {
                logger.warn("Transaction can not be rollbacked: {}", ex.getMessage());
            }
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Finds all patients
     *
     * @return list of found patients
     * @throws PersistentException
     */
    @Override
    public List<Patient> findAll() throws ServicePersistentException {
        PatientDao patientDao = transaction.createPatientDao();
        try {
            List<Patient> patients = patientDao.read();
            if (!patients.isEmpty()) {
                buildPatient(patients);
                return patients;
            } else {
                throw new ServicePersistentException("Empty list of patients");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Finds all patients with specified offset and number of records
     *
     * @param offset
     * @param noOfRecords
     * @return
     * @throws ServicePersistentException
     */
    @Override
    public Map<Integer, List<Patient>> find(int offset, int noOfRecords) throws ServicePersistentException {
        try {
            PatientDao patientDao = transaction.createPatientDao();
            return patientDao.read(offset, noOfRecords);
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Finds patient by email
     *
     * @param email
     * @return found patient
     * @throws ServicePersistentException
     */
    @Override
    public Patient findByEmail(String email) throws ServicePersistentException {
        PatientDao patientDao = transaction.createPatientDao();
        try {
            Patient patient = patientDao.readByEmail(email);
            if (patient != null) {
                buildPatient(Collections.singletonList(patient));
                return patient;
            } else {
                throw new ServicePersistentException("There is no such patient");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Finds patient by id
     *
     * @param id
     * @return found patient
     * @throws ServicePersistentException
     */
    @Override
    public Patient findById(Integer id) throws ServicePersistentException {
        PatientDao patientDao = transaction.createPatientDao();
        try {
            Patient patient = patientDao.read(id);
            if (patient != null) {
                buildPatient(Collections.singletonList(patient));
                return patient;
            } else {
                throw new ServicePersistentException("There is no such patient");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Finds all diseases
     *
     * @return list of diseases
     * @throws ServicePersistentException
     */
    @Override
    public List<String> findDiseases() throws ServicePersistentException {
        PatientDao patientDao = transaction.createPatientDao();
        try {
            List<String> diseases = patientDao.readDiseases();
            if (!diseases.isEmpty()) {
                return diseases;
            } else {
                throw new ServicePersistentException("List of diseases is empty");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Finds all patient's diseases
     *
     * @return
     * @throws ServicePersistentException
     */
    @Override
    public List<String> findDiseasesByPatient(Integer patientId) throws ServicePersistentException {
        PatientDao patientDao = transaction.createPatientDao();
        try {
            List<String> diseases = patientDao.readDiseasesByPatient(patientId);
            if (!diseases.isEmpty()) {
                return diseases;
            } else {
                throw new ServicePersistentException("List of patient's diseases is empty");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Fills patients with corresponding fields
     *
     * @param patients
     * @throws ServicePersistentException
     */
    private void buildPatient(List<Patient> patients) throws ServicePersistentException {
        UserDao userDao = transaction.createUserDao();
        try {
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
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }
}
