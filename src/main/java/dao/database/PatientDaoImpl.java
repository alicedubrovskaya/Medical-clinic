package dao.database;

import dao.PatientDao;
import domain.Patient;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDaoImpl extends BaseDaoImpl implements PatientDao {
    private final Logger logger = LogManager.getLogger(getClass().getName());
    private static final String CREATE_PATIENT =
            "INSERT INTO `patient`(`id`,`name`, `surname`, `email`, `phone_number`, `address`) " +
                    "VALUES (?,?,?,?,?,?)";

    private static final String READ_PATIENT = "SELECT `name`, `surname`, `email`, `phone_number`," +
            " `address` FROM `patient` WHERE `id`=? ";

    private static final String READ_PATIENT_BY_EMAIL =
            "SELECT `id`, `name`, `surname`, `phone_number`, `address` FROM `patient`" +
                    " WHERE `email`=?  ";

    private static final String UPDATE_PATIENT = "UPDATE `patient` " +
            "SET `name`=?, `surname`=?, `email`=?, `phone_number`=?, `address`=?" +
            " WHERE `id` = ?";

    private static final String DELETE_PATIENT = "DELETE FROM `patient` WHERE `id` = ?";

    @Override
    public Integer create(Patient patient) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(CREATE_PATIENT);
            statement.setInt(1, patient.getId());
            statement.setString(2, patient.getName());
            statement.setString(3, patient.getSurname());
            statement.setString(4, patient.getEmail());
            statement.setString(5, patient.getPhoneNumber());
            statement.setString(6, patient.getAddress());

            statement.executeUpdate();
            return patient.getId();
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
    public Patient read(Integer id) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_PATIENT);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Patient patient = null;
            if (resultSet.next()) {
                patient = new Patient();
                patient.setId(id);
                patient.setName(resultSet.getString("name"));
                patient.setSurname(resultSet.getString("surname"));
                patient.setEmail(resultSet.getString("email"));
                patient.setPhoneNumber(resultSet.getString("phone_number"));
                patient.setAddress(resultSet.getString("address"));
            }
            return patient;
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
    public void update(Patient patient) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_PATIENT);
            statement.setString(1, patient.getName());
            statement.setString(2, patient.getSurname());
            statement.setString(3, patient.getEmail());
            statement.setString(4, patient.getPhoneNumber());
            statement.setString(5, patient.getAddress());
            statement.setInt(6, patient.getId());

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
            statement = connection.prepareStatement(DELETE_PATIENT);
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
    public Patient readByEmail(String email) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_PATIENT_BY_EMAIL);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            Patient patient = null;
            if (resultSet.next()) {
                patient = new Patient();
                patient.setId(resultSet.getInt("id"));
                patient.setName(resultSet.getString("name"));
                patient.setSurname(resultSet.getString("surname"));
                patient.setEmail(email);
                patient.setPhoneNumber(resultSet.getString("phone_number"));
                patient.setAddress(resultSet.getString("address"));
            }
            return patient;
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
