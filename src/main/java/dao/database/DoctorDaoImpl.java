package dao.database;

import dao.DoctorDao;
import domain.Doctor;
import domain.enumeration.Shift;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DoctorDaoImpl extends BaseDaoImpl implements DoctorDao {
    private final Logger logger = LogManager.getLogger(getClass().getName());
    private static final String CREATE_DOCTOR = "INSERT INTO `doctor` (`id`, `name`, `surname`," +
            " `specialization_id`, `working_shift`) VALUES (?,?,?,?,?)";

    private static final String READ_DOCTOR_BY_ID = "SELECT `name`, `surname`, `specialization_id`," +
            "`working_shift` FROM `doctor` WHERE id=?";

    private static final String READ_DOCTOR = "SELECT `id`, `name`, `surname`, `specialization_id`," +
            "`working_shift` FROM `doctor`";

    private static final String READ_DOCTORS_WITHOUT_VACATION_DAY = "SELECT * from `doctor` LEFT JOIN vacation v " +
            "ON doctor.id = v.doctor_id WHERE doctor_id is NULL OR (? NOT BETWEEN `start` AND `end`)";


    private static final String READ_DOCTOR_BY_SPECIALIZATION = "SELECT `id`, `name`, `surname` " +
            "`working_shift` FROM `doctor` WHERE specialization_id=?";

    private static final String READ_SPECIALIZATION_BY_TYPE = "SELECT `id` FROM `specialization` " +
            "WHERE `type`=?";

    private static final String READ_SPECIALIZATION_BY_ID = "SELECT `type` FROM `specialization` " +
            "WHERE `id`=?";

    private static final String UPDATE_DOCTOR = "UPDATE `doctor` " +
            "SET `name`=?, `surname`=?, `specialization_id`=?, `working_shift`=? WHERE `id` = ?";

    private static final String DELETE_DOCTOR = "DELETE FROM `doctor` WHERE `id` = ?";

    @Override
    public Integer create(Doctor doctor) throws PersistentException {
        PreparedStatement statement = null;
        PreparedStatement specializationStatement = null;
        ResultSet resultSet = null;
        try {
            specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_TYPE);
            specializationStatement.setString(1, doctor.getSpecialization());
            resultSet = specializationStatement.executeQuery();
            Integer specializationID = null;
            if (resultSet.next()) {
                specializationID = resultSet.getInt("id");
            }

            statement = connection.prepareStatement(CREATE_DOCTOR);
            statement.setInt(1, doctor.getId());
            statement.setString(2, doctor.getName());
            statement.setString(3, doctor.getSurname());
            statement.setInt(4, specializationID);
            statement.setInt(5, doctor.getWorkingShift().getId());

            statement.executeUpdate();
            return doctor.getId();

        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
                specializationStatement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public Doctor read(Integer id) throws PersistentException {
        PreparedStatement statement = null;
        PreparedStatement specializationStatement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_DOCTOR_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Doctor doctor = null;
            if (resultSet.next()) {
                doctor = new Doctor();
                doctor.setId(id);
                doctor.setName(resultSet.getString("name"));
                doctor.setSurname(resultSet.getString("surname"));
                doctor.setWorkingShift(Shift.getById(resultSet.getInt("working_shift")));

                specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_ID);
                specializationStatement.setInt(1, resultSet.getInt("specialization_id"));
                resultSet = specializationStatement.executeQuery();
                if (resultSet.next()) {
                    doctor.setSpecialization(resultSet.getString("type"));
                }
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
                specializationStatement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public void update(Doctor doctor) throws PersistentException {
        PreparedStatement statement = null;
        PreparedStatement specializationStatement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(UPDATE_DOCTOR);
            statement.setString(1, doctor.getName());
            statement.setString(2, doctor.getSurname());
            statement.setInt(4, doctor.getWorkingShift().getId());
            statement.setInt(5, doctor.getId());

            specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_TYPE);
            specializationStatement.setString(1, doctor.getSpecialization());
            resultSet = specializationStatement.executeQuery();
            if (resultSet.next()) {
                statement.setInt(3, resultSet.getInt("id"));
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                statement.close();
                specializationStatement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_DOCTOR);
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
    public List<Doctor> read() throws PersistentException {
        PreparedStatement statement = null;
        PreparedStatement specializationStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSetSpecialization = null;
        try {
            statement = connection.prepareStatement(READ_DOCTOR);
            resultSet = statement.executeQuery();
            Doctor doctor = null;
            List<Doctor> doctors = new ArrayList<>();
            while (resultSet.next()) {
                doctor = new Doctor();
                doctor.setId(resultSet.getInt("id"));
                doctor.setName(resultSet.getString("name"));
                doctor.setSurname(resultSet.getString("surname"));
                doctor.setWorkingShift(Shift.getById(resultSet.getInt("working_shift")));

                specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_ID);
                specializationStatement.setInt(1, resultSet.getInt("specialization_id"));
                resultSetSpecialization = specializationStatement.executeQuery();
                if (resultSetSpecialization.next()) {
                    doctor.setSpecialization(resultSetSpecialization.getString("type"));
                }
                doctors.add(doctor);
            }
            return doctors;
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (resultSetSpecialization != null) {
                    resultSetSpecialization.close();
                }
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
                specializationStatement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public List<Doctor> readWithoutVacation(Date date) throws PersistentException {
        PreparedStatement statement = null;
        PreparedStatement specializationStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSetSpecialization = null;
        try {
            statement = connection.prepareStatement(READ_DOCTORS_WITHOUT_VACATION_DAY);
            statement.setDate(1, new java.sql.Date(date.getTime()));
            resultSet = statement.executeQuery();

            List<Doctor> doctors = new ArrayList<>();
            Doctor doctor = null;
            while (resultSet.next()) {
                doctor = new Doctor();
                doctor.setId(resultSet.getInt("id"));
                doctor.setName(resultSet.getString("name"));
                doctor.setSurname(resultSet.getString("surname"));
                doctor.setWorkingShift(Shift.getById(resultSet.getInt("working_shift")));

                specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_ID);
                specializationStatement.setInt(1, resultSet.getInt("specialization_id"));
                resultSetSpecialization = specializationStatement.executeQuery();
                if (resultSetSpecialization.next()) {
                    doctor.setSpecialization(resultSetSpecialization.getString("type"));
                }
                doctors.add(doctor);
            }
            return doctors;
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (resultSetSpecialization != null) {
                    resultSetSpecialization.close();
                }
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
                specializationStatement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public List<Doctor> readBySpecializationType(String specialization) throws PersistentException {
        PreparedStatement statement = null;
        PreparedStatement specializationStatement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_DOCTOR_BY_SPECIALIZATION);

            specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_TYPE);
            specializationStatement.setString(1, specialization);
            resultSet = specializationStatement.executeQuery();
            if (resultSet.next()) {
                statement.setInt(1, resultSet.getInt("id"));
            }

            resultSet = statement.executeQuery();
            List<Doctor> doctors = new ArrayList<>();
            Doctor doctor;
            if (resultSet.next()) {
                doctor = new Doctor();
                doctor.setId(resultSet.getInt("id"));
                doctor.setName(resultSet.getString("name"));
                doctor.setSurname(resultSet.getString("surname"));
                doctor.setSpecialization(specialization);
                doctor.setWorkingShift(Shift.getById(resultSet.getInt("working_shift")));
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
                specializationStatement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }
}
