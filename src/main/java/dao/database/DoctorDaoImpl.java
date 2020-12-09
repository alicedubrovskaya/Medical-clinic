package dao.database;

import dao.ConnectorDB;
import dao.DoctorDao;
import domain.Doctor;
import domain.Specialization;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDaoImpl extends BaseDaoImpl implements DoctorDao {
    private final Logger logger = LogManager.getLogger(getClass().getName());
    private static final String CREATE_DOCTOR = "INSERT INTO `doctor` (`name`, `surname`, `specialization`) " +
            "VALUES (?,?,?)";
    private static final String READ_DOCTOR = "SELECT `name`, `surname`, `specialization` " +
            " FROM `doctor` WHERE id=?";
    private static final String READ_DOCTOR_BY_SPECIALIZATION = "SELECT `id`, `name`, `surname` " +
            " FROM `doctor` WHERE specialization=?";
    private static final String UPDATE_DOCTOR = "UPDATE `doctor` " +
            "SET `name`=?, `surname`=?, `specialization`=? WHERE `id` = ?";
    private static final String DELETE_DOCTOR = "DELETE FROM `doctor` WHERE `id` = ?";

    public DoctorDaoImpl() {
        this.connector = new ConnectorDB();
    }

    @Override
    public Integer create(Doctor doctor) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = connector.getConnection();
            statement = connection.prepareStatement(CREATE_DOCTOR, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, doctor.getName());
            statement.setString(2, doctor.getSurname());
            statement.setInt(3, doctor.getSpecialization().getId());

            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                logger.error("There is no autoincremented index after trying to add record into table `doctor`");
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
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public Doctor read(Integer id) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connector.getPreparedStatement(READ_DOCTOR);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Doctor doctor = null;
            if (resultSet.next()) {
                doctor = new Doctor();
                doctor.setId(id);
                doctor.setName(resultSet.getString("name"));
                doctor.setSurname(resultSet.getString("surname"));
                doctor.setSpecialization(Specialization.getById(
                        resultSet.getInt("specialization"))
                );
            }
            return doctor;
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
    public void update(Doctor doctor) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connector.getPreparedStatement(UPDATE_DOCTOR);
            statement.setString(1, doctor.getName());
            statement.setString(2, doctor.getSurname());
            statement.setInt(3, doctor.getSpecialization().getId());
            statement.setInt(4, doctor.getId());
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
            statement = connector.getPreparedStatement(DELETE_DOCTOR);
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
    public List<Doctor> readBySpecialization(Specialization specialization) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connector.getPreparedStatement(READ_DOCTOR_BY_SPECIALIZATION);
            statement.setInt(1, specialization.getId());
            resultSet = statement.executeQuery();
            List<Doctor> doctors = new ArrayList<>();
            Doctor doctor = null;
            if (resultSet.next()) {
                doctor = new Doctor();
                doctor.setId(resultSet.getInt("id"));
                doctor.setName(resultSet.getString("name"));
                doctor.setSurname(resultSet.getString("surname"));
                doctor.setSpecialization(specialization);
                doctors.add(doctor);
            }
            return doctors;
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
}
