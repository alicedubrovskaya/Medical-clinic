package by.dubrovskaya.dao.impl;

import by.dubrovskaya.dao.AppointmentDao;
import by.dubrovskaya.dao.extractor.AppointmentExtractor;
import by.dubrovskaya.dao.extractor.Extractor;
import by.dubrovskaya.dao.extractor.PatientExtractor;
import by.dubrovskaya.domain.Appointment;
import by.dubrovskaya.domain.Doctor;
import by.dubrovskaya.domain.Patient;
import by.dubrovskaya.domain.enumeration.Shift;
import by.dubrovskaya.domain.enumeration.Status;
import by.dubrovskaya.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AppointmentDaoImpl extends BaseDaoImpl implements AppointmentDao {
    private final Extractor<Appointment> appointmentExtractor;
    private static final Logger logger = LogManager.getLogger(AppointmentDaoImpl.class);

    public AppointmentDaoImpl() {
        appointmentExtractor = new AppointmentExtractor();
    }

    private static final String ID = "id";
    private static final String PATIENT_ID = "patient_id";
    private static final String DOCTOR_ID = "doctor_id";
    private static final String SUCCESSFUL_READING_OF_APPOINTMENT = "Appointment was read";
    private static final String SUCCESSFUL_READING_OF_APPOINTMENTS = "Appointments were read";

    private static final String CREATE_APPOINTMENT = "INSERT INTO `appointment` (`time`, `approved`," +
            "`status`, `complaints`, `medical_report`, `recommendation`, `patient_id`, `doctor_id`)" +
            " VALUES (:dasd,?,?,?,?,?,?,?)";
    //TODO

    private static final String READ_APPOINTMENTS = "SELECT `id`, `time`, `approved`,  `status`, `complaints`," +
            " `medical_report`,`recommendation`, `patient_id`, `doctor_id` FROM `appointment`";

    private static final String READ_APPOINTMENT_BY_ID = READ_APPOINTMENTS + " WHERE `id`=?";

    private static final String READ_APPOINTMENT_BY_TIME = READ_APPOINTMENTS + " WHERE `time` BETWEEN ? and ?";

    private static final String READ_APPOINTMENT_BY_PATIENT = READ_APPOINTMENTS + " WHERE `patient_id`=? AND `status`= 0";

    private static final String READ_APPOINTMENT_BY_TIME_AND_DOCTOR = READ_APPOINTMENTS + " WHERE `time`=? AND `doctor_id`=?";

    private static final String READ_APPOINTMENTS_BY_TIME_AND_STATUS_AND_DOCTOR = READ_APPOINTMENTS +
            " WHERE `status`= ? AND `time` BETWEEN ? and ? AND `doctor_id`=?";

    private static final String READ_APPOINTMENTS_BY_TIME_AND_STATUS = READ_APPOINTMENTS +
            " WHERE `status`= ? AND `time` BETWEEN ? and ?";

    private static final String READ_APPOINTMENT_BY_PATIENT_AND_DISEASE = "SELECT `time`, `complaints`, `medical_report`," +
            " `recommendation` FROM `appointment` JOIN patient_disease on appointment.id = patient_disease.appointment_id " +
            "WHERE patient_disease.patient_id = ? AND disease_id=?";

    private static final String READ_APPOINTMENTS_BY_TIME_AND_SPECIALIZATION = "SELECT appointment.id, `approved`, `status`," +
            " `complaints`, `medical_report`, `recommendation`, `patient_id`, `doctor_id`, `time` FROM `appointment`" +
            "JOIN doctor d on d.id = appointment.doctor_id WHERE d.specialization_id=? AND" +
            "`time` BETWEEN ? and ?";

    private static final String READ_DISEASE_BY_NAME = "SELECT `id` FROM `disease` WHERE `name` =?";
    private static final String READ_SPECIALIZATION_BY_NAME = "SELECT `id` FROM `specialization` WHERE `type` =?";

    private static final String UPDATE_APPOINTMENT = "UPDATE `appointment` SET `time` =?,`approved`=?," +
            " `status`=?, `complaints`=?, `medical_report`=?, `recommendation`=?, `patient_id`=?," +
            " `doctor_id`=? WHERE `id` = ?";

    private static final String DELETE_APPOINTMENT = "DELETE FROM `appointment` WHERE `id`=?";

    /**
     * Creates appointment in database
     *
     * @param appointment that should be created
     * @return appointment id
     * @throws PersistentException if database error occurs
     */
    @Override
    public Integer create(Appointment appointment) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_APPOINTMENT, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setTimestamp(1, new Timestamp(appointment.getTime().getTime()));
            statement.setBoolean(2, appointment.isApproved());
            statement.setInt(3, appointment.getStatus().getId());
            statement.setString(4, appointment.getComplaints());
            statement.setString(5, appointment.getMedicalReport());
            statement.setString(6, appointment.getRecommendation());
            if (appointment.getPatient() != null && appointment.getPatient().getId() != null) {
                statement.setInt(7, appointment.getPatient().getId());
            } else {
                statement.setNull(7, Types.INTEGER);
            }
            if (appointment.getDoctor() != null && appointment.getDoctor().getId() != null) {
                statement.setInt(8, appointment.getDoctor().getId());
            } else {
                statement.setNull(8, Types.INTEGER);
            }

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            Integer appointmentId;
            if (resultSet.next()) {
                appointmentId = resultSet.getInt(1);
                logger.debug("Appointment with id={} was created", appointmentId);
            } else {
                throw new PersistentException("There is no autoincrement id after trying to add record into table `appointment`");
            }
            return appointmentId;
        } catch (SQLException e) {
            throw new PersistentException("Appointment cannot be created", e);
        }
    }

    /**
     * Reads an appointment by id
     *
     * @param id - unique identifier
     * @return found appointment
     * @throws PersistentException if database error occurs
     */
    @Override
    public Appointment read(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_APPOINTMENT_BY_ID)
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Appointment appointment = null;
            if (resultSet.next()) {
                appointment = appointmentExtractor.extract(resultSet);
                Integer patientId = resultSet.getInt(PATIENT_ID);
                if (!resultSet.wasNull()) {
                    Patient patient = new Patient();
                    patient.setId(patientId);
                    appointment.setPatient(patient);
                }
                Integer doctorId = resultSet.getInt(DOCTOR_ID);
                if (!resultSet.wasNull()) {
                    Doctor doctor = new Doctor();
                    doctor.setId(doctorId);
                    appointment.setDoctor(doctor);
                }
            }
            logger.debug(SUCCESSFUL_READING_OF_APPOINTMENT);
            return appointment;
        } catch (SQLException e) {
            throw new PersistentException("Appointment cannot be found");
        }
    }

    /**
     * Updates appointment
     *
     * @param appointment that should be updated
     * @throws PersistentException if database error occurs
     */
    @Override
    public void update(Appointment appointment) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_APPOINTMENT)) {
            statement.setTimestamp(1, new Timestamp(appointment.getTime().getTime()));
            statement.setBoolean(2, appointment.isApproved());
            statement.setInt(3, appointment.getStatus().getId());
            statement.setString(4, appointment.getComplaints());
            statement.setString(5, appointment.getMedicalReport());
            statement.setString(6, appointment.getRecommendation());
            if (appointment.getPatient() != null && appointment.getPatient().getId() != null) {
                statement.setInt(7, appointment.getPatient().getId());
            } else {
                statement.setNull(7, Types.INTEGER);
            }
            if (appointment.getDoctor() != null && appointment.getDoctor().getId() != null) {
                statement.setInt(8, appointment.getDoctor().getId());
            } else {
                statement.setNull(8, Types.INTEGER);
            }
            statement.setInt(9, appointment.getId());
            statement.executeUpdate();
            logger.debug("Appointment was updated");
        } catch (SQLException e) {
            throw new PersistentException("Appointment cannot be updated");
        }
    }

    /**
     * Deletes appointment by id
     *
     * @param id of appointment
     * @throws PersistentException if database error occurs
     */
    @Override
    public void delete(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_APPOINTMENT)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.debug("Appointment with id = {} was deleted", id);
        } catch (SQLException e) {
            throw new PersistentException("Appointment cannot be deleted");
        }
    }

    /**
     * Creates appointments by date for specified doctor
     *
     * @param date   of appointment
     * @param doctor
     * @return created appointments
     */
    @Override
    public List<Appointment> createAppointments(Date date, Doctor doctor) {
        long start;
        long end;
        if (doctor.getWorkingShift().equals(Shift.FIRST)) {
            start = date.getTime() + TimeUnit.HOURS.toMillis(8);
            end = date.getTime() + TimeUnit.HOURS.toMillis(13);
        } else {
            start = date.getTime() + TimeUnit.HOURS.toMillis(14);
            end = date.getTime() + TimeUnit.HOURS.toMillis(19);
        }

        long currentTime = start;
        List<Appointment> appointments = new ArrayList<>();
        Appointment appointment;
        while (currentTime < end) {
            appointment = new Appointment();
            appointment.setTime(new Date(currentTime));
            appointment.setApproved(false);
            appointment.setDoctor(doctor);
            appointment.setStatus(Status.MISSED);
            currentTime += TimeUnit.MINUTES.toMillis(20);
            appointments.add(appointment);
        }
        return appointments;
    }


    /**
     * Reads all appointments
     *
     * @return found appointments
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Appointment> readAll() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_APPOINTMENTS)) {
            ResultSet resultSet = statement.executeQuery();
            Appointment appointment;
            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                appointment = appointmentExtractor.extract(resultSet);
                Integer patientId = resultSet.getInt(PATIENT_ID);
                if (!resultSet.wasNull()) {
                    Patient patient = new Patient();
                    patient.setId(patientId);
                    appointment.setPatient(patient);
                }
                Integer doctorId = resultSet.getInt(DOCTOR_ID);
                if (!resultSet.wasNull()) {
                    Doctor doctor = new Doctor();
                    doctor.setId(doctorId);
                    appointment.setDoctor(doctor);
                }
                appointments.add(appointment);
            }
            logger.debug(SUCCESSFUL_READING_OF_APPOINTMENTS);
            return appointments;
        } catch (SQLException e) {
            throw new PersistentException("Appointments cannot be found");
        }
    }

    /**
     * Reads appointments by time
     *
     * @param date of appointment
     * @return found appointments
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Appointment> readByTime(Date date) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_APPOINTMENT_BY_TIME)) {
            statement.setTimestamp(1, new Timestamp(date.getTime()));
            statement.setTimestamp(2, new Timestamp(date.getTime() + TimeUnit.DAYS.toMillis(1)));

            ResultSet resultSet = statement.executeQuery();
            Appointment appointment;
            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                appointment = appointmentExtractor.extract(resultSet);
                Integer patientId = resultSet.getInt(PATIENT_ID);
                if (!resultSet.wasNull()) {
                    Patient patient = new Patient();
                    patient.setId(patientId);
                    appointment.setPatient(patient);
                }
                Integer doctorId = resultSet.getInt(DOCTOR_ID);
                if (!resultSet.wasNull()) {
                    Doctor doctor = new Doctor();
                    doctor.setId(doctorId);
                    appointment.setDoctor(doctor);
                }
                appointments.add(appointment);
            }
            logger.debug(SUCCESSFUL_READING_OF_APPOINTMENTS);
            return appointments;
        } catch (SQLException e) {
            throw new PersistentException("Appointments cannot be found");
        }
    }

    /**
     * Reads appointments by patient
     *
     * @param patientId patient's id
     * @return found appointments
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Appointment> readByPatient(Integer patientId) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_APPOINTMENT_BY_PATIENT)) {
            statement.setInt(1, patientId);
            ResultSet resultSet = statement.executeQuery();
            Appointment appointment;
            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                appointment = appointmentExtractor.extract(resultSet);

                Patient patient = new Patient();
                patient.setId(patientId);
                appointment.setPatient(patient);

                Integer doctorId = resultSet.getInt(DOCTOR_ID);
                if (!resultSet.wasNull()) {
                    Doctor doctor = new Doctor();
                    doctor.setId(doctorId);
                    appointment.setDoctor(doctor);
                }
                appointments.add(appointment);
            }
            logger.debug(SUCCESSFUL_READING_OF_APPOINTMENTS);
            return appointments;
        } catch (SQLException e) {
            throw new PersistentException("Appointments were not found");
        }
    }

    /**
     * Reads appointments by time and doctor
     *
     * @param date   of appointment
     * @param doctor
     * @return
     * @throws PersistentException if database error occurs
     */
    @Override
    public Appointment readByTimeAndDoctor(Date date, Doctor doctor) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_APPOINTMENT_BY_TIME_AND_DOCTOR)) {
            statement.setTimestamp(1, new Timestamp(date.getTime()));
            statement.setInt(2, doctor.getId());
            ResultSet resultSet = statement.executeQuery();
            Appointment appointment = null;
            if (resultSet.next()) {
                appointment = appointmentExtractor.extract(resultSet);
                Integer patientId = resultSet.getInt(PATIENT_ID);
                if (!resultSet.wasNull()) {
                    Patient patient = new Patient();
                    patient.setId(patientId);
                    appointment.setPatient(patient);
                }
                appointment.setDoctor(doctor);
            }
            logger.debug(SUCCESSFUL_READING_OF_APPOINTMENT);
            return appointment;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * Reads appointments by time and specialization
     *
     * @param date           of appointment
     * @param specialization of a doctor
     * @return found appointments
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Appointment> readByTimeAndSpecialization(Date date, String specialization) throws PersistentException {
        try (PreparedStatement statementSpecialization = connection.prepareStatement(READ_SPECIALIZATION_BY_NAME);
             PreparedStatement statement = connection.prepareStatement(READ_APPOINTMENTS_BY_TIME_AND_SPECIALIZATION)) {
            statementSpecialization.setString(1, specialization);
            ResultSet resultSet = statementSpecialization.executeQuery();

            int specializationId;
            List<Appointment> appointments = new ArrayList<>();
            if (resultSet.next()) {
                specializationId = resultSet.getInt(ID);

                statement.setInt(1, specializationId);
                statement.setTimestamp(2, new Timestamp(date.getTime()));
                statement.setTimestamp(3, new Timestamp(date.getTime() + TimeUnit.DAYS.toMillis(1)));
                resultSet = statement.executeQuery();
                Appointment appointment;
                while (resultSet.next()) {
                    appointment = appointmentExtractor.extract(resultSet);
                    int patientId = resultSet.getInt(PATIENT_ID);
                    Integer doctorId = resultSet.getInt(DOCTOR_ID);
                    if (!resultSet.wasNull()) {
                        Doctor doctor = new Doctor();
                        doctor.setId(doctorId);
                        appointment.setDoctor(doctor);
                    }
                    if (patientId == 0) {
                        appointments.add(appointment);
                    }
                }
            }
            return appointments;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * Reads appointments by date, status and doctor
     *
     * @param date     of appointment
     * @param status   of appointment
     * @param doctorId doctor's id
     * @return found appointments
     * @throws PersistentException if database error occurs
     */
    public List<Appointment> readByDateAndStatusAndDoctor(Date date, String status, Integer doctorId) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_APPOINTMENTS_BY_TIME_AND_STATUS_AND_DOCTOR)) {
            Integer statusId = Status.getEnum(status).equals(Status.MISSED) ? Status.MISSED.getId() : Status.WAS.getId();

            statement.setInt(1, statusId);
            statement.setTimestamp(2, new Timestamp(date.getTime()));
            statement.setTimestamp(3, new Timestamp(date.getTime() + TimeUnit.DAYS.toMillis(1)));
            statement.setInt(4, doctorId);
            ResultSet resultSet = statement.executeQuery();
            Appointment appointment;
            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                appointment = appointmentExtractor.extract(resultSet);
                Integer patientId = resultSet.getInt(PATIENT_ID);
                if (!resultSet.wasNull()) {
                    Patient patient = new Patient();
                    patient.setId(patientId);
                    appointment.setPatient(patient);
                }
                doctorId = resultSet.getInt(DOCTOR_ID);
                if (!resultSet.wasNull()) {
                    Doctor doctor = new Doctor();
                    doctor.setId(doctorId);
                    appointment.setDoctor(doctor);
                }
                appointments.add(appointment);
            }
            logger.debug(SUCCESSFUL_READING_OF_APPOINTMENTS);
            return appointments;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * Reads appointments by date and status
     *
     * @param date   of appointment
     * @param status of appointment
     * @return found appointments
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Appointment> readByDateAndStatus(Date date, String status) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_APPOINTMENTS_BY_TIME_AND_STATUS)) {
            Integer statusId = Status.getEnum(status).equals(Status.MISSED) ? Status.MISSED.getId() : Status.WAS.getId();
            //TODO move
            statement.setInt(1, statusId);
            statement.setTimestamp(2, new Timestamp(date.getTime()));
            statement.setTimestamp(3, new Timestamp(date.getTime() + TimeUnit.DAYS.toMillis(1)));
            ResultSet resultSet = statement.executeQuery();
            Appointment appointment;
            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                appointment = appointmentExtractor.extract(resultSet);
                Integer patientId = resultSet.getInt(PATIENT_ID);
                if (!resultSet.wasNull()) {
                    Patient patient = new Patient();
                    patient.setId(patientId);
                    appointment.setPatient(patient);
                }
                Integer doctorId = resultSet.getInt(DOCTOR_ID);
                if (!resultSet.wasNull()) {
                    Doctor doctor = new Doctor();
                    doctor.setId(doctorId);
                    appointment.setDoctor(doctor);
                }
                appointments.add(appointment);
            }
            logger.debug(SUCCESSFUL_READING_OF_APPOINTMENTS);
            return appointments;
        } catch (SQLException e) {
            throw new PersistentException("Appointments were not found", e);
        }
    }

    /**
     * Reads appointment by patient and disease
     *
     * @param patientId   patient's id
     * @param diseaseName name of disease
     * @return found appointment
     * @throws PersistentException if database error occurs
     */
    @Override
    public Appointment readByPatientAndDisease(Integer patientId, String diseaseName) throws PersistentException {
        try (PreparedStatement statementDisease = connection.prepareStatement(READ_DISEASE_BY_NAME);
             PreparedStatement statement = connection.prepareStatement(READ_APPOINTMENT_BY_PATIENT_AND_DISEASE)
        ) {
            statementDisease.setString(1, diseaseName);
            ResultSet resultSetDisease = statementDisease.executeQuery();

            int diseaseId;
            Appointment appointment = null;
            if (resultSetDisease.next()) {
                diseaseId = resultSetDisease.getInt(ID);
                statement.setInt(1, patientId);
                statement.setInt(2, diseaseId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    appointment = appointmentExtractor.extract(resultSet);
                }
            }
            logger.debug(SUCCESSFUL_READING_OF_APPOINTMENT);
            return appointment;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }
}
