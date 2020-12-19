package service.impl;

import dao.AppointmentDao;
import dao.DoctorDao;
import dao.PatientDao;
import domain.Appointment;
import domain.Doctor;
import domain.Patient;
import exception.PersistentException;
import service.AppointmentService;

import java.util.*;

public class AppointmentServiceImpl extends ServiceImpl implements AppointmentService {
    @Override
    public void save(Appointment appointment) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createDao(AppointmentDao.class);
        if (appointment.getId() != null) {
            appointmentDao.update(appointment);
        } else {
            appointmentDao.create(appointment);
        }
    }

    @Override
    public void saveGenerated(Appointment appointment) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createDao(AppointmentDao.class);
        Appointment existingAppointment = findByTimeAndDoctor(appointment.getTime(), appointment.getDoctor());
        if (existingAppointment == null) {
            appointmentDao.create(appointment);
        }
    }

    @Override
    public Appointment findById(Integer id) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createDao(AppointmentDao.class);
        Appointment appointment = appointmentDao.read(id);
        if (appointment != null) {
            buildAppointment(Arrays.asList(appointment));
        }
        return appointment;
    }

    @Override
    public List<Appointment> findByTime(Date date) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createDao(AppointmentDao.class);
        List<Appointment> appointments = appointmentDao.readByTime(date);
        buildAppointment(appointments);
        return appointments;
    }

    @Override
    public Appointment findByTimeAndDoctor(Date date, Doctor doctor) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createDao(AppointmentDao.class);
        Appointment appointment = appointmentDao.readByTimeAndDoctor(date, doctor);
        if (appointment != null) {
            buildAppointment(Arrays.asList(appointment));
        }
        return appointment;
    }

    @Override
    public Appointment findByPatientAndDisease(Integer patientId, String diseaseName) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createDao(AppointmentDao.class);
        return appointmentDao.readByPatientAndDisease(patientId, diseaseName);
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createDao(AppointmentDao.class);
        appointmentDao.delete(id);
    }

    @Override
    public List<Appointment> createAppointments(Date date, Doctor doctor) throws PersistentException {
        AppointmentDao appointmentDao = transaction.createDao(AppointmentDao.class);
        List<Appointment> appointments = appointmentDao.createAppointments(date, doctor);
        for (Appointment appointment : appointments) {
            saveGenerated(appointment);
        }
        return appointments;
    }

    private void buildAppointment(List<Appointment> appointments) throws PersistentException {
        DoctorDao doctorDao = transaction.createDao(DoctorDao.class);
        PatientDao patientDao = transaction.createDao(PatientDao.class);

        Map<Integer, Doctor> doctors = new HashMap<>();
        Map<Integer, Patient> patients = new HashMap<>();
        Integer id;
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
        }
    }
}
