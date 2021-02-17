package by.dubrovskaya.service.impl;

import by.dubrovskaya.dao.AppointmentDao;
import by.dubrovskaya.dao.DoctorDao;
import by.dubrovskaya.dao.PatientDao;
import by.dubrovskaya.dao.VacationDao;
import by.dubrovskaya.domain.Appointment;
import by.dubrovskaya.domain.Doctor;
import by.dubrovskaya.domain.Patient;
import by.dubrovskaya.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.dubrovskaya.service.AppointmentService;
import by.dubrovskaya.exception.ServicePersistentException;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class AppointmentServiceImpl extends ServiceImpl implements AppointmentService {
    private static final Logger logger = LogManager.getLogger(AppointmentServiceImpl.class);

    @Override
    public void save(Appointment appointment) throws ServicePersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        try {
            if (appointment.getId() != null) {
                appointmentDao.update(appointment);
            } else {
                appointment.setId(appointmentDao.create(appointment));
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void save(Appointment appointment, String disease) throws ServicePersistentException {
        transaction.setWithoutAutoCommit();
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        PatientDao patientDao = transaction.createPatientDao();
        try {
            appointmentDao.update(appointment);
            patientDao.saveDiseaseForPatient(appointment.getPatient().getId(), appointment.getId(), disease);
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

    @Override
    public List<Appointment> findAll() throws ServicePersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        try {
            List<Appointment> appointments = appointmentDao.readAll();
            if (!appointments.isEmpty()) {
                buildAppointment(appointments);
                return appointments;
            } else {
                throw new ServicePersistentException("Empty list of appointments");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public Appointment findById(Integer id) throws ServicePersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        try {
            Appointment appointment = appointmentDao.read(id);
            if (appointment != null) {
                buildAppointment(Arrays.asList(appointment));
                return appointment;
            } else {
                throw new ServicePersistentException("Appointment not found");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public List<Appointment> findByTime(Date date) throws ServicePersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        try {
            List<Appointment> appointments = appointmentDao.readByTime(date);
            if (!appointments.isEmpty()) {
                buildAppointment(appointments);
                return appointments;
            } else {
                throw new ServicePersistentException("Empty list of appointments");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public Appointment findByTimeAndDoctor(Date date, Doctor doctor) throws ServicePersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        try {
            Appointment appointment = appointmentDao.readByTimeAndDoctor(date, doctor);
            if (appointment != null) {
                buildAppointment(Collections.singletonList(appointment));
                return appointment;
            } else {
                throw new ServicePersistentException("Appointment is not found");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public List<Appointment> findByTimeAndSpecialization(Date date, String specialization) throws ServicePersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        try {
            List<Appointment> appointments = appointmentDao.readByTimeAndSpecialization(date, specialization);
            if (appointments != null) {
                buildAppointment(appointments);
                return appointments;
            } else {
                throw new ServicePersistentException("Appointment not found");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    public List<Appointment> findByDateAndStatusAndDoctor(Date date, String status, Integer doctorId) throws
            ServicePersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        try {
            List<Appointment> appointments = appointmentDao.readByDateAndStatusAndDoctor(date, status, doctorId);
            if (!appointments.isEmpty()) {
                buildAppointment(appointments);
                return appointments;
            } else {
                throw new ServicePersistentException("Empty list of appointments");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public List<Appointment> findByDateAndStatus(Date date, String status) throws ServicePersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        try {
            List<Appointment> appointments = appointmentDao.readByDateAndStatus(date, status);
            if (!appointments.isEmpty()) {
                buildAppointment(appointments);
                return appointments;
            } else {
                throw new ServicePersistentException("Empty list of appointments");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public List<Appointment> findByPatient(Integer patientId) throws ServicePersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        try {
            List<Appointment> appointments = appointmentDao.readByPatient(patientId);
            if (!appointments.isEmpty()) {
                buildAppointment(appointments);
                return appointments;
            } else {
                throw new ServicePersistentException("List of appointments is empty");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void delete(Integer id) throws ServicePersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        try {
            appointmentDao.delete(id);
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    private List<Appointment> createAppointments(Date date, Doctor doctor) throws ServicePersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        VacationDao vacationDao = transaction.createVacationDao();
        List<Appointment> appointments = new ArrayList<>();

        try {
            if (defineDayOfWeek(date) != 1 && defineDayOfWeek(date) != 7 && vacationDao.readBySpecifiedDate(date) == null) {
                appointments = appointmentDao.createAppointments(date, doctor);
                for (Appointment appointment : appointments) {
                    save(appointment);
                }
            }
            return appointments;
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }


    @Override
    public void createAppointmentsForDoctors(Date date, int countOfDays) throws ServicePersistentException {
        transaction.setWithoutAutoCommit();
        AppointmentDao appointmentDao = transaction.createAppointmentDao();

        long currentDate = date.getTime();
        long lastDate = date.getTime() + TimeUnit.DAYS.toMillis(countOfDays);

        DoctorDao doctorDao = transaction.createDoctorDao();
        List<Doctor> doctors;
        Date appointmentDate;

        try {
            while (currentDate < lastDate) {
                appointmentDate = new Date(currentDate);
                doctors = doctorDao.readWithoutVacation(appointmentDate);
                List<Appointment> existingAppointmentsOnThisDate = appointmentDao.readByTime(appointmentDate);
                if (existingAppointmentsOnThisDate.isEmpty()) {
                    for (Doctor doctor : doctors) {
                        createAppointments(appointmentDate, doctor);
                    }
                } else {
                    logger.warn("Appointments are already generated for this date");
                }
                doctors.clear();
                currentDate += TimeUnit.DAYS.toMillis(1);
            }
            transaction.commit();
        } catch (
                PersistentException e) {
            try {
                transaction.rollback();
            } catch (PersistentException ex) {
                logger.warn("Transaction can not be rollbacked:{} ", ex.getMessage());
            }
            throw new ServicePersistentException("Appointments for doctors were not created");
        }

    }

    private void buildAppointment(List<Appointment> appointments) throws ServicePersistentException {
        DoctorDao doctorDao = transaction.createDoctorDao();
        PatientDao patientDao = transaction.createPatientDao();

        Map<Integer, Doctor> doctors = new HashMap<>();
        Map<Integer, Patient> patients = new HashMap<>();
        Integer id;
        String disease;
        Doctor doctor;
        Patient patient;

        try {
            for (Appointment appointment : appointments) {
                doctor = appointment.getDoctor();
                if (doctor != null) {
                    id = doctor.getId();
                    doctor = doctors.get(id);
                    if (doctor == null) {
                        doctor = doctorDao.read(id);
                        doctors.put(doctor.getId(), doctor);
                    }
                    appointment.setDoctor(doctor);
                }

                patient = appointment.getPatient();
                if (patient != null) {
                    id = patient.getId();
                    patient = patients.get(id);
                    if (patient == null) {
                        patient = patientDao.read(id);
                        patients.put(patient.getId(), patient);
                    }
                    appointment.setPatient(patient);
                }

                if (appointment.getPatient() != null) {
                    disease = patientDao.readDiseaseByAppointment(appointment.getPatient().getId(), appointment.getId());
                    if (disease != null) {
                        appointment.setDisease(disease);
                    }
                }
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    private int defineDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
}
