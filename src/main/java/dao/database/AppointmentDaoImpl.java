package dao.database;

import dao.AppointmentDao;
import domain.Appointment;
import domain.Doctor;
import domain.Patient;
import domain.enumeration.Shift;
import domain.enumeration.Status;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AppointmentDaoImpl extends BaseDaoImpl implements AppointmentDao {
    private static final String CREATE_APPOINTMENT = "INSERT INTO `appointment` (`time`, `approved`," +
            "`status`, `complaints`, `medical_report`, `recommendation`, `patient_id`, `doctor_id`)" +
            " VALUES (?,?,?,?,?,?,?,?)";

    private static final String READ_APPOINTMENT = "SELECT `time`, `approved`,  `status`, `complaints`," +
            " `medical_report`,`recommendation`, `patient_id`, `doctor_id` FROM `appointment` WHERE `id`=?";

    private static final String READ_APPOINTMENTS = "SELECT `id`, `time`, `approved`,  `status`, `complaints`," +
            " `medical_report`,`recommendation`, `patient_id`, `doctor_id` FROM `appointment`";

    private static final String READ_APPOINTMENT_BY_TIME = "SELECT `id`, `approved`, `status`, `complaints`," +
            " `medical_report`, `recommendation`, `patient_id`, `doctor_id` " +
            " FROM `appointment` WHERE `time` BETWEEN ? and ?";

    private static final String READ_APPOINTMENT_BY_PATIENT = "SELECT `id`, `approved`, `complaints`," +
            " `medical_report`, `time`,`recommendation`, `doctor_id` " +
            " FROM `appointment` WHERE `patient_id`=? AND `status`= 0";

    private static final String READ_APPOINTMENT_BY_TIME_AND_DOCTOR = "SELECT `id`, `approved`, `status`, `complaints`," +
            " `medical_report`, `recommendation`, `patient_id` " +
            " FROM `appointment` WHERE `time`=? AND `doctor_id`=?";

    private static final String READ_APPOINTMENTS_BY_TIME_AND_SPECIALIZATION = "SELECT appointment.id, `approved`, `status`," +
            " `complaints`, `medical_report`, `recommendation`, `patient_id`, `doctor_id`, `time` FROM `appointment`" +
            "JOIN doctor d on d.id = appointment.doctor_id WHERE d.specialization_id=? AND" +
            "`time` BETWEEN ? and ?";

    private static final String READ_APPOINTMENTS_BY_TIME_AND_STATUS_AND_DOCTOR = "SELECT `id`, `approved`, `complaints`," +
            " `medical_report`, `time`,`recommendation`, `doctor_id`,  `patient_id` " +
            " FROM `appointment` WHERE `status`= ? AND `time` BETWEEN ? and ? AND `doctor_id`=?";

    private static final String READ_APPOINTMENTS_BY_TIME_AND_STATUS = "SELECT `id`, `approved`, `complaints`," +
            " `medical_report`, `time`,`recommendation`, `doctor_id`,  `patient_id` " +
            " FROM `appointment` WHERE `status`= ? AND `time` BETWEEN ? and ?";

    private static final String READ_APPOINTMENT_BY_PATIENT_AND_DISEASE = "SELECT `time`, `complaints`, `medical_report`," +
            " `recommendation` FROM `appointment` JOIN patient_disease on appointment.id = patient_disease.appointment_id " +
            "WHERE patient_disease.patient_id = ? AND disease_id=?";

    private static final String READ_DISEASE_BY_NAME = "SELECT `id` FROM `disease` WHERE `name` =?";
    private static final String READ_SPECIALIZATION_BY_NAME = "SELECT `id` FROM `specialization` WHERE `type` =?";


    private static final String UPDATE_APPOINTMENT = "UPDATE `appointment` SET `time` =?,`approved`=?," +
            " `status`=?, `complaints`=?, `medical_report`=?, `recommendation`=?, `patient_id`=?," +
            " `doctor_id`=? WHERE `id` = ?";

    private static final String DELETE_APPOINTMENT = "DELETE FROM `appointment` WHERE `id`=?";
    private final Logger logger = LogManager.getLogger(getClass().getName());

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
                throw new PersistentException("There is no autoincremented id after trying to add record into table `appointment`");
            }
            return appointmentId;
        } catch (SQLException e) {
            throw new PersistentException("Appointment wasn't created");
        }
    }

    @Override
    public Appointment read(Integer id) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_APPOINTMENT);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Appointment appointment = null;
            if (resultSet.next()) {
                appointment = new Appointment();
                appointment.setId(id);
                appointment.setTime(resultSet.getTimestamp("time"));
                appointment.setApproved(resultSet.getBoolean("approved"));
                appointment.setStatus(Status.getById(resultSet.getInt("status")));
                appointment.setComplaints(resultSet.getString("complaints"));
                appointment.setMedicalReport(resultSet.getString("medical_report"));
                appointment.setRecommendation(resultSet.getString("recommendation"));
                Integer patientId = resultSet.getInt("patient_id");
                if (!resultSet.wasNull()) {
                    Patient patient = new Patient();
                    patient.setId(patientId);
                    appointment.setPatient(patient);
                }
                Integer doctorId = resultSet.getInt("doctor_id");
                if (!resultSet.wasNull()) {
                    Doctor doctor = new Doctor();
                    doctor.setId(doctorId);
                    appointment.setDoctor(doctor);
                }
            }
            return appointment;
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public void update(Appointment appointment) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_APPOINTMENT);
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
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_APPOINTMENT);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public List<Appointment> createAppointments(Date date, Doctor doctor) throws PersistentException {
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


    @Override
    public List<Appointment> readAll() throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ResultSet resultSetTimetable = null;
        try {
            statement = connection.prepareStatement(READ_APPOINTMENTS);
            resultSet = statement.executeQuery();
            Appointment appointment = null;
            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                appointment = new Appointment();
                appointment.setId(resultSet.getInt("id"));
                appointment.setTime(resultSet.getTimestamp("time"));
                appointment.setApproved(resultSet.getBoolean("approved"));
                appointment.setStatus(Status.getById(resultSet.getInt("status")));
                appointment.setComplaints(resultSet.getString("complaints"));
                appointment.setMedicalReport(resultSet.getString("medical_report"));
                appointment.setRecommendation(resultSet.getString("recommendation"));
                Integer patientId = resultSet.getInt("patient_id");
                if (!resultSet.wasNull()) {
                    Patient patient = new Patient();
                    patient.setId(patientId);
                    appointment.setPatient(patient);
                }
                Integer doctorId = resultSet.getInt("doctor_id");
                if (!resultSet.wasNull()) {
                    Doctor doctor = new Doctor();
                    doctor.setId(doctorId);
                    appointment.setDoctor(doctor);
                }
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
                resultSetTimetable.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public List<Appointment> readByTime(Date date) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ResultSet resultSetTimetable = null;
        try {
            statement = connection.prepareStatement(READ_APPOINTMENT_BY_TIME);
            statement.setTimestamp(1, new Timestamp(date.getTime()));
            statement.setTimestamp(2, new Timestamp(date.getTime() + TimeUnit.DAYS.toMillis(1)));

            resultSet = statement.executeQuery();
            Appointment appointment = null;
            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                appointment = new Appointment();
                appointment.setId(resultSet.getInt("id"));
                appointment.setTime(date);
                appointment.setApproved(resultSet.getBoolean("approved"));
                appointment.setStatus(Status.getById(resultSet.getInt("status")));
                appointment.setComplaints(resultSet.getString("complaints"));
                appointment.setMedicalReport(resultSet.getString("medical_report"));
                appointment.setRecommendation(resultSet.getString("recommendation"));
                Integer patientId = resultSet.getInt("patient_id");
                if (!resultSet.wasNull()) {
                    Patient patient = new Patient();
                    patient.setId(patientId);
                    appointment.setPatient(patient);
                }
                Integer doctorId = resultSet.getInt("doctor_id");
                if (!resultSet.wasNull()) {
                    Doctor doctor = new Doctor();
                    doctor.setId(doctorId);
                    appointment.setDoctor(doctor);
                }
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
                resultSetTimetable.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public List<Appointment> readByPatient(Integer patientId) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ResultSet resultSetTimetable = null;
        try {
            statement = connection.prepareStatement(READ_APPOINTMENT_BY_PATIENT);
            statement.setInt(1, patientId);
            resultSet = statement.executeQuery();
            Appointment appointment = null;
            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                appointment = new Appointment();
                appointment.setId(resultSet.getInt("id"));
                appointment.setTime(resultSet.getTimestamp("time"));
                appointment.setApproved(resultSet.getBoolean("approved"));
                appointment.setComplaints(resultSet.getString("complaints"));
                appointment.setMedicalReport(resultSet.getString("medical_report"));
                appointment.setRecommendation(resultSet.getString("recommendation"));

                Patient patient = new Patient();
                patient.setId(patientId);
                appointment.setPatient(patient);

                Integer doctorId = resultSet.getInt("doctor_id");
                if (!resultSet.wasNull()) {
                    Doctor doctor = new Doctor();
                    doctor.setId(doctorId);
                    appointment.setDoctor(doctor);
                }
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
                resultSetTimetable.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public Appointment readByTimeAndDoctor(Date date, Doctor doctor) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ResultSet resultSetTimetable = null;
        try {
            statement = connection.prepareStatement(READ_APPOINTMENT_BY_TIME_AND_DOCTOR);
            statement.setTimestamp(1, new Timestamp(date.getTime()));
            statement.setInt(2, doctor.getId());
            resultSet = statement.executeQuery();
            Appointment appointment = null;
            if (resultSet.next()) {
                appointment = new Appointment();
                appointment.setId(resultSet.getInt("id"));
                appointment.setTime(date);
                appointment.setApproved(resultSet.getBoolean("approved"));
                appointment.setStatus(Status.getById(resultSet.getInt("status")));
                appointment.setComplaints(resultSet.getString("complaints"));
                appointment.setMedicalReport(resultSet.getString("medical_report"));
                appointment.setRecommendation(resultSet.getString("recommendation"));
                Integer patientId = resultSet.getInt("patient_id");
                if (!resultSet.wasNull()) {
                    Patient patient = new Patient();
                    patient.setId(patientId);
                    appointment.setPatient(patient);
                }
                appointment.setDoctor(doctor);
            }
            return appointment;
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
                resultSetTimetable.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public List<Appointment> readByTimeAndSpecialization(Date date, String specialization) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_SPECIALIZATION_BY_NAME);
            statement.setString(1, specialization);
            resultSet = statement.executeQuery();

            Integer specializationId = null;
            List<Appointment> appointments = new ArrayList<>();
            if (resultSet.next()) {
                specializationId = resultSet.getInt("id");

                statement = connection.prepareStatement(READ_APPOINTMENTS_BY_TIME_AND_SPECIALIZATION);
                statement.setInt(1, specializationId);
                statement.setTimestamp(2, new Timestamp(date.getTime()));
                statement.setTimestamp(3, new Timestamp(date.getTime() + TimeUnit.DAYS.toMillis(1)));
                resultSet = statement.executeQuery();
                Appointment appointment = null;
                while (resultSet.next()) {
                    appointment = new Appointment();
                    appointment.setId(resultSet.getInt("id"));
                    appointment.setTime(resultSet.getTimestamp("time"));
                    appointment.setApproved(resultSet.getBoolean("approved"));
                    appointment.setStatus(Status.getById(resultSet.getInt("status")));
                    appointment.setComplaints(resultSet.getString("complaints"));
                    appointment.setMedicalReport(resultSet.getString("medical_report"));
                    appointment.setRecommendation(resultSet.getString("recommendation"));
                    Integer patientId = resultSet.getInt("patient_id");
                    Integer doctorId = resultSet.getInt("doctor_id");
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
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }


    public List<Appointment> readByDateAndStatusAndDoctor(Date date, String status, Integer doctorId) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_APPOINTMENTS_BY_TIME_AND_STATUS_AND_DOCTOR);
            Integer statusId;
            //TODO move
            if (status.equals("Не был")) {
                statusId = Status.MISSED.getId();
            } else {
                statusId = Status.WAS.getId();
            }
            statement.setInt(1, statusId);
            statement.setTimestamp(2, new Timestamp(date.getTime()));
            statement.setTimestamp(3, new Timestamp(date.getTime() + TimeUnit.DAYS.toMillis(1)));
            statement.setInt(4, doctorId);
            resultSet = statement.executeQuery();
            Appointment appointment = null;
            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                appointment = new Appointment();
                appointment.setId(resultSet.getInt("id"));
                appointment.setTime(resultSet.getTimestamp("time"));
                appointment.setApproved(resultSet.getBoolean("approved"));
                appointment.setStatus(Status.getById(statusId));
                appointment.setComplaints(resultSet.getString("complaints"));
                appointment.setMedicalReport(resultSet.getString("medical_report"));
                appointment.setRecommendation(resultSet.getString("recommendation"));
                Integer patientId = resultSet.getInt("patient_id");
                if (!resultSet.wasNull()) {
                    Patient patient = new Patient();
                    patient.setId(patientId);
                    appointment.setPatient(patient);
                }
                doctorId = resultSet.getInt("doctor_id");
                if (!resultSet.wasNull()) {
                    Doctor doctor = new Doctor();
                    doctor.setId(doctorId);
                    appointment.setDoctor(doctor);
                }
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public List<Appointment> readByDateAndStatus(Date date, String status) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_APPOINTMENTS_BY_TIME_AND_STATUS);
        ) {
            Integer statusId;
            //TODO move
            if (status.equals("Не был")) {
                statusId = Status.MISSED.getId();
            } else {
                statusId = Status.WAS.getId();
            }
            statement.setInt(1, statusId);
            statement.setTimestamp(2, new Timestamp(date.getTime()));
            statement.setTimestamp(3, new Timestamp(date.getTime() + TimeUnit.DAYS.toMillis(1)));
            ResultSet resultSet = statement.executeQuery();
            Appointment appointment = null;
            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                appointment = new Appointment();
                appointment.setId(resultSet.getInt("id"));
                appointment.setTime(resultSet.getTimestamp("time"));
                appointment.setApproved(resultSet.getBoolean("approved"));
                appointment.setStatus(Status.getById(statusId));
                appointment.setComplaints(resultSet.getString("complaints"));
                appointment.setMedicalReport(resultSet.getString("medical_report"));
                appointment.setRecommendation(resultSet.getString("recommendation"));
                Integer patientId = resultSet.getInt("patient_id");
                if (!resultSet.wasNull()) {
                    Patient patient = new Patient();
                    patient.setId(patientId);
                    appointment.setPatient(patient);
                }
                Integer doctorId = resultSet.getInt("doctor_id");
                if (!resultSet.wasNull()) {
                    Doctor doctor = new Doctor();
                    doctor.setId(doctorId);
                    appointment.setDoctor(doctor);
                }
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException e) {
            throw new PersistentException("Appointments were not found", e);
        }
    }

    @Override
    public Appointment readByPatientAndDisease(Integer patientId, String diseaseName) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSetDisease = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_DISEASE_BY_NAME);
            statement.setString(1, diseaseName);
            resultSetDisease = statement.executeQuery();

            Integer diseaseId = null;
            Appointment appointment = null;
            if (resultSetDisease.next()) {
                diseaseId = resultSetDisease.getInt("id");
                statement = connection.prepareStatement(READ_APPOINTMENT_BY_PATIENT_AND_DISEASE);
                statement.setInt(1, patientId);
                statement.setInt(2, diseaseId);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    appointment = new Appointment();
                    appointment.setTime(resultSet.getTimestamp("time"));
                    appointment.setComplaints(resultSet.getString("complaints"));
                    appointment.setMedicalReport(resultSet.getString("medical_report"));
                    appointment.setRecommendation(resultSet.getString("recommendation"));
                }
            }
            return appointment;
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
                resultSetDisease.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }
}
