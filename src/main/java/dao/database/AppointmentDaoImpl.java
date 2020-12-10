package dao.database;

import dao.AppointmentDao;
import dao.ConnectorDB;
import domain.Appointment;
import domain.Doctor;
import domain.MedicalCard;
import domain.enumeration.Status;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDaoImpl extends BaseDaoImpl implements AppointmentDao {
    private static final String CREATE_APPOINTMENT = "INSERT INTO `appointment` (`time`, `approved`," +
            " `status`, `complaints`, `medical_report`, `recommendation`, `medical_card_id`, `doctor_id`, " +
            "`timetable_id`) VALUES (?,?,?,?,?,?,?,?,?)";

    private static final String READ_APPOINTMENT = "SELECT `time`, `approved`,  `status`, `complaints`, `medical_report`," +
            " `recommendation`, `medical_card_id`, `doctor_id`, `timetable_id` FROM `appointment` WHERE `id`=?";

    private static final String READ_APPOINTMENT_BY_TIME = "SELECT `id`, `approved`, `status`, `complaints`," +
            " `medical_report`, `recommendation`, `medical_card_id`, `doctor_id`, `timetable_id`" +
            " FROM `appointment` WHERE `time`=?";

    private static final String READ_TIMETABLE_BY_NAME = "SELECT `id` FROM `timetable` " +
            "WHERE `name`=?";

    private static final String READ_TIMETABLE_BY_ID = "SELECT `name` FROM `timetable` " +
            "WHERE `id`=?";

    private static final String UPDATE_APPOINTMENT = "UPDATE `appointment` SET `time` =?,`approved`=?," +
            " `status`=?, `complaints`=?, `medical_report`=?, `recommendation`=?, `medical_card_id`=?," +
            " `doctor_id`=?, `timetable_id`=? WHERE `id` = ?";

    private static final String DELETE_APPOINTMENT = "DELETE FROM `appointment` WHERE `id`=?";
    private final Logger logger = LogManager.getLogger(getClass().getName());


    public AppointmentDaoImpl() {
        this.connector = new ConnectorDB();
    }

    @Override
    public Integer create(Appointment appointment) throws PersistentException {
        PreparedStatement statement = null;
        PreparedStatement timetableStatement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = connector.getConnection();

            timetableStatement = connection.prepareStatement(READ_TIMETABLE_BY_NAME);
            timetableStatement.setString(1, appointment.getTimetable());
            resultSet = timetableStatement.executeQuery();
            Integer timetableID = null;
            if (resultSet.next()) {
                timetableID = resultSet.getInt("id");
            }

            statement = connection.prepareStatement(CREATE_APPOINTMENT, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, appointment.getTime());
            statement.setBoolean(2, appointment.isApproved());
            statement.setInt(3, appointment.getStatus().getId());
            statement.setString(4, appointment.getComplaints());
            statement.setString(5, appointment.getMedicalReport());
            statement.setString(6, appointment.getRecommendation());
            if (appointment.getMedicalCard() != null && appointment.getMedicalCard().getId() != null) {
                statement.setInt(7, appointment.getMedicalCard().getId());
            } else {
                statement.setNull(7, Types.INTEGER);
            }
            if (appointment.getDoctor() != null && appointment.getDoctor().getId() != null) {
                statement.setInt(8, appointment.getDoctor().getId());
            } else {
                statement.setNull(8, Types.INTEGER);
            }
            statement.setInt(9, timetableID);

            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                logger.error("There is no autoincremented index after trying to add record into table `appointment`");
                throw new PersistentException();
            }
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
                timetableStatement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public Appointment read(Integer id) throws PersistentException {
        PreparedStatement statement = null;
        PreparedStatement timetableStatement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = connector.getConnection();
            statement = connection.prepareStatement(READ_APPOINTMENT);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Appointment appointment = null;
            if (resultSet.next()) {
                appointment = new Appointment();
                appointment.setId(id);
                appointment.setTime(resultSet.getDate("time"));
                appointment.setApproved(resultSet.getBoolean("approved"));
                appointment.setStatus(Status.getById(resultSet.getInt("status")));
                appointment.setComplaints(resultSet.getString("complaints"));
                appointment.setMedicalReport(resultSet.getString("medical_report"));
                appointment.setRecommendation(resultSet.getString("recommendation"));
                Integer medicalId = resultSet.getInt("medical_card_id");
                if (!resultSet.wasNull()) {
                    MedicalCard medicalCard = new MedicalCard();
                    medicalCard.setId(medicalId);
                    appointment.setMedicalCard(medicalCard);
                }
                Integer doctorId = resultSet.getInt("doctor_id");
                if (!resultSet.wasNull()) {
                    Doctor doctor = new Doctor();
                    doctor.setId(doctorId);
                    appointment.setDoctor(doctor);
                }
                timetableStatement = connection.prepareStatement(READ_TIMETABLE_BY_ID);
                timetableStatement.setInt(1, resultSet.getInt("timetable_id"));
                resultSet = timetableStatement.executeQuery();
                if (resultSet.next()) {
                    appointment.setTimetable(resultSet.getString("name"));
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
                timetableStatement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public void update(Appointment appointment) throws PersistentException {
        PreparedStatement statement = null;
        PreparedStatement timetableStatement = null;
        ResultSet resultSet= null;
        try {
            Connection connection = connector.getConnection();
            statement = connection.prepareStatement(UPDATE_APPOINTMENT);
            statement.setDate(1, appointment.getTime());
            statement.setBoolean(2, appointment.isApproved());
            statement.setInt(3, appointment.getStatus().getId());
            statement.setString(4, appointment.getComplaints());
            statement.setString(5, appointment.getMedicalReport());
            statement.setString(6, appointment.getRecommendation());
            if (appointment.getMedicalCard() != null && appointment.getMedicalCard().getId() != null) {
                statement.setInt(7, appointment.getMedicalCard().getId());
            } else {
                statement.setNull(7, Types.INTEGER);
            }
            if (appointment.getDoctor() != null && appointment.getDoctor().getId() != null) {
                statement.setInt(8, appointment.getDoctor().getId());
            } else {
                statement.setNull(8, Types.INTEGER);
            }

            timetableStatement = connection.prepareStatement(READ_TIMETABLE_BY_NAME);
            timetableStatement.setString(1, appointment.getTimetable());
            resultSet = timetableStatement.executeQuery();
            Integer timetableID = null;
            if (resultSet.next()) {
                timetableID = resultSet.getInt("id");
            }

            statement.setInt(9, timetableID);
            statement.setInt(10, appointment.getId());

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
            statement = connector.getPreparedStatement(DELETE_APPOINTMENT);
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
    public List<Appointment> readByTime(Date date) throws PersistentException {
        PreparedStatement statement = null;
        PreparedStatement timetableStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSetTimetable=null;
        try {
            Connection connection = connector.getConnection();

            statement = connection.prepareStatement(READ_APPOINTMENT_BY_TIME);
            statement.setDate(1, date);
            resultSet = statement.executeQuery();
            Appointment appointment = null;
            List<Appointment> appointments = new ArrayList<>();
            if (resultSet.next()) {
                appointment = new Appointment();
                appointment.setId(resultSet.getInt("id"));
                appointment.setTime(date);
                appointment.setApproved(resultSet.getBoolean("approved"));
                appointment.setStatus(Status.getById(resultSet.getInt("status")));
                appointment.setComplaints(resultSet.getString("complaints"));
                appointment.setMedicalReport(resultSet.getString("medical_report"));
                appointment.setRecommendation(resultSet.getString("recommendation"));
                Integer medicalId = resultSet.getInt("medical_card_id");
                if (!resultSet.wasNull()) {
                    MedicalCard medicalCard = new MedicalCard();
                    medicalCard.setId(medicalId);
                    appointment.setMedicalCard(medicalCard);
                }
                Integer doctorId = resultSet.getInt("doctor_id");
                if (!resultSet.wasNull()) {
                    Doctor doctor = new Doctor();
                    doctor.setId(doctorId);
                    appointment.setDoctor(doctor);
                }
                timetableStatement = connection.prepareStatement(READ_TIMETABLE_BY_ID);
                timetableStatement.setInt(1, resultSet.getInt("timetable_id"));
                resultSetTimetable = timetableStatement.executeQuery();
                if (resultSetTimetable.next()) {
                    appointment.setTimetable(resultSetTimetable.getString("name"));
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
                timetableStatement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }
}
