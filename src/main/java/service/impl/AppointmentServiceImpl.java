package service.impl;

import dao.AppointmentDao;
import dao.DoctorDao;
import dao.PatientDao;
import dao.VacationDao;
import domain.Appointment;
import domain.Doctor;
import domain.Patient;
import domain.User;
import domain.enumeration.Role;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AppointmentService;
import service.PasswordEncryption;
import service.exception.ServicePersistentException;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class AppointmentServiceImpl extends ServiceImpl implements AppointmentService {
    private static final Logger logger = LogManager.getLogger(AppointmentServiceImpl.class);

    @Override
    public void save(Appointment appointment) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        if (appointment.getId() != null) {
            appointmentDao.update(appointment);
        } else {
            appointment.setId(appointmentDao.create(appointment));
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


    private void saveGenerated(Appointment appointment) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        Appointment existingAppointment = findByTimeAndDoctor(appointment.getTime(), appointment.getDoctor());
        if (existingAppointment == null) {
            appointment.setId(appointmentDao.create(appointment));
        }
    }

    @Override
    public List<Appointment> findAll() throws PersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        List<Appointment> appointments = appointmentDao.readAll();
        buildAppointment(appointments);
        return appointments;
    }

    @Override
    public Appointment findById(Integer id) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        Appointment appointment = appointmentDao.read(id);
        if (appointment != null) {
            buildAppointment(Arrays.asList(appointment));
        }
        return appointment;
    }

    @Override
    public List<Appointment> findByTime(Date date) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        List<Appointment> appointments = appointmentDao.readByTime(date);
        buildAppointment(appointments);
        return appointments;
    }

    @Override
    public Appointment findByTimeAndDoctor(Date date, Doctor doctor) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        Appointment appointment = appointmentDao.readByTimeAndDoctor(date, doctor);
        if (appointment != null) {
            buildAppointment(Arrays.asList(appointment));
        }
        return appointment;
    }

    @Override
    public List<Appointment> findByTimeAndSpecialization(Date date, String specialization) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        List<Appointment> appointments = appointmentDao.readByTimeAndSpecialization(date, specialization);
        if (appointments != null) {
            buildAppointment(appointments);
        }
        return appointments;
    }

    public List<Appointment> findByDateAndStatusAndDoctor(Date date, String status, Integer doctorId) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        List<Appointment> appointments = appointmentDao.readByDateAndStatusAndDoctor(date, status, doctorId);
        if (appointments != null) {
            buildAppointment(appointments);
        }
        return appointments;
    }

    @Override
    public List<Appointment> findByDateAndStatus(Date date, String status) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        List<Appointment> appointments = appointmentDao.readByDateAndStatus(date, status);
        if (appointments != null) {
            buildAppointment(appointments);
        }
        return appointments;
    }

    @Override
    public Appointment findByPatientAndDisease(Integer patientId, String diseaseName) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        return appointmentDao.readByPatientAndDisease(patientId, diseaseName);
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
    public void delete(Integer id) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        appointmentDao.delete(id);
    }

    private List<Appointment> createAppointments(Date date, Doctor doctor) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createAppointmentDao();
        VacationDao vacationDao = transaction.createVacationDao();
        List<Appointment> appointments = new ArrayList<>();

        if (defineDayOfWeek(date) != 1 && defineDayOfWeek(date) != 7 && vacationDao.readBySpecifiedDate(date) == null) {
            appointments = appointmentDao.createAppointments(date, doctor);
            for (Appointment appointment : appointments) {
                saveGenerated(appointment);
            }
        }
        return appointments;
    }

    @Override
    public void createAppointmentsForDoctors(Date date, int countOfDays) throws PersistentException {
        transaction.setWithoutAutoCommit();
        long currentDate = date.getTime();
        long lastDate = date.getTime() + TimeUnit.DAYS.toMillis(countOfDays);

        DoctorDao doctorDao = transaction.createDoctorDao();
        List<Doctor> doctors;
        Date appointmentDate;

        try {
            while (currentDate < lastDate) {
                appointmentDate = new Date(currentDate);
                doctors = doctorDao.readWithoutVacation(appointmentDate);
                for (Doctor doctor : doctors) {
                    createAppointments(appointmentDate, doctor);
                }
                doctors.clear();
                currentDate += TimeUnit.DAYS.toMillis(1);
            }
            transaction.commit();
        } catch (PersistentException e) {
            transaction.rollback();
            //TODO service exception
            throw new PersistentException();
        }
    }

    private void buildAppointment(List<Appointment> appointments) throws PersistentException {
        DoctorDao doctorDao = transaction.createDoctorDao();
        PatientDao patientDao = transaction.createPatientDao();

        Map<Integer, Doctor> doctors = new HashMap<>();
        Map<Integer, Patient> patients = new HashMap<>();
        Integer id;
        String disease;
        Doctor doctor;
        Patient patient;

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
    }

    private int defineDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
}
