package service.impl;

import dao.DoctorDao;
import dao.UserDao;
import domain.Doctor;
import domain.User;
import domain.enumeration.Role;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.DoctorService;
import service.PasswordEncryption;
import service.exception.ServicePersistentException;

import java.util.Collections;
import java.util.List;

/**
 * Doctor service
 */
public class DoctorServiceImpl extends ServiceImpl implements DoctorService {
    private static final Logger logger = LogManager.getLogger(DoctorServiceImpl.class);

    /**
     * Saves doctor
     *
     * @param doctor
     * @throws ServicePersistentException
     */
    @Override
    public void save(Doctor doctor) throws ServicePersistentException {
        transaction.setWithoutAutoCommit();
        UserDao userDao = transaction.createUserDao();
        DoctorDao doctorDao = transaction.createDoctorDao();
        try {
            if (doctor.getId() == null) {
                Integer userId = userDao.create(new User(doctor.getLogin(),
                        PasswordEncryption.encrypt(doctor.getPassword()), Role.DOCTOR)
                );
                doctor.setId(userId);
                doctorDao.create(doctor);
            } else {
                doctorDao.update(doctor);
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
     * Finds doctor by id
     *
     * @param id
     * @return found doctor
     * @throws ServicePersistentException
     */
    @Override
    public Doctor findById(Integer id) throws ServicePersistentException {
        DoctorDao doctorDao = transaction.createDoctorDao();
        try {
            Doctor doctor = doctorDao.read(id);
            if (doctor != null) {
                buildDoctor(Collections.singletonList(doctor));
            } else {
                throw new ServicePersistentException("There is no such doctor");
            }
            return doctor;
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Finds doctor by surname and name
     *
     * @param surname
     * @param name
     * @return found doctor
     * @throws ServicePersistentException
     */
    @Override
    public Doctor findBySurnameAndName(String surname, String name) throws ServicePersistentException {
        DoctorDao doctorDao = transaction.createDoctorDao();
        try {
            Doctor doctor = doctorDao.readBySurnameAndName(surname, name);
            if (doctor != null) {
                buildDoctor(Collections.singletonList(doctor));
            } else {
                throw new ServicePersistentException("There is no such doctor");
            }
            return doctor;
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Finds doctors by specialization
     *
     * @param specialization
     * @return list of found doctors
     * @throws ServicePersistentException
     */
    @Override
    public List<Doctor> findBySpecializationType(String specialization) throws ServicePersistentException {
        DoctorDao doctorDao = transaction.createDoctorDao();
        try {
            List<Doctor> doctors = doctorDao.readBySpecializationType(specialization);
            if (!doctors.isEmpty()) {
                buildDoctor(doctors);
                return doctors;
            } else {
                throw new ServicePersistentException("Empty list of doctors");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Finds doctors
     *
     * @return list of found doctors
     * @throws ServicePersistentException
     */
    @Override
    public List<Doctor> findAll() throws ServicePersistentException {
        DoctorDao doctorDao = transaction.createDoctorDao();
        try {
            List<Doctor> doctors = doctorDao.read();
            if (!doctors.isEmpty()) {
                buildDoctor(doctors);
                return doctors;
            } else {
                throw new ServicePersistentException("Empty list of doctors");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }

    }

    /**
     * Finds all specializations
     *
     * @return list of specializations
     * @throws ServicePersistentException
     */
    @Override
    public List<String> findAllSpecializations() throws ServicePersistentException {
        DoctorDao doctorDao = transaction.createDoctorDao();
        try {
            List<String> specializations = doctorDao.readSpecializations();
            if (!specializations.isEmpty()) {
                return specializations;
            } else {
                throw new ServicePersistentException("Empty list of specializations");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Deletes doctor by id
     *
     * @param id
     * @throws ServicePersistentException
     */
    @Override
    public void delete(Integer id) throws ServicePersistentException {
        transaction.setWithoutAutoCommit();
        UserDao userDao = transaction.createUserDao();
        DoctorDao doctorDao = transaction.createDoctorDao();
        try {
            doctorDao.delete(id);
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
     * Fills doctors with corresponding fields
     *
     * @param doctors - doctors to fill with data
     * @throws ServicePersistentException if filling error occurs
     */
    private void buildDoctor(List<Doctor> doctors) throws ServicePersistentException {
        UserDao userDao = transaction.createUserDao();
        try {
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
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }
}
