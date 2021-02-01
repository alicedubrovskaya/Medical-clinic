package dao.database;

import dao.DoctorDao;
import domain.Doctor;
import domain.Patient;
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

/**
 * Data access object for doctor
 */
public class DoctorDaoImpl extends BaseDaoImpl implements DoctorDao {
    private static final Logger logger = LogManager.getLogger(DoctorDaoImpl.class);

    private static final String CREATE_DOCTOR = "INSERT INTO `doctor` (`id`, `name`, `surname`," +
            " `specialization_id`, `working_shift`) VALUES (?,?,?,?,?)";

    private static final String READ_DOCTOR_BY_ID = "SELECT `name`, `surname`, `specialization_id`," +
            "`working_shift` FROM `doctor` WHERE id=?";

    private static final String READ_DOCTOR = "SELECT `id`, `name`, `surname`, `specialization_id`," +
            "`working_shift` FROM `doctor`";

    private static final String READ_DOCTORS_WITHOUT_VACATION_DAY = "SELECT * from `doctor` LEFT JOIN vacation v " +
            "ON doctor.id = v.doctor_id WHERE doctor_id is NULL OR (? NOT BETWEEN `start` AND `end`)";

    private static final String READ_DOCTOR_BY_SURNAME_AND_NAME = "SELECT `id`, `name`, `surname`, " +
            "`working_shift`, `specialization_id` FROM `doctor` WHERE name=? AND surname=?";

    private static final String READ_DOCTOR_BY_SPECIALIZATION = "SELECT `id`, `name`, `surname` " +
            "`working_shift` FROM `doctor` WHERE specialization_id=?";

    private static final String READ_SPECIALIZATION_BY_TYPE = "SELECT `id` FROM `specialization` " +
            "WHERE `type`=?";

    private static final String READ_SPECIALIZATION_BY_ID = "SELECT `type` FROM `specialization` " +
            "WHERE `id`=?";

    private static final String READ_SPECIALIZATIONS = "SELECT `type` FROM specialization";

    private static final String UPDATE_DOCTOR = "UPDATE `doctor` " +
            "SET `name`=?, `surname`=?, `specialization_id`=?, `working_shift`=? WHERE `id` = ?";

    private static final String DELETE_DOCTOR = "DELETE FROM `doctor` WHERE `id` = ?";

    /**
     * Creates doctor in database
     *
     * @param doctor
     * @return generated key
     * @throws PersistentException
     */
    @Override
    public Integer create(Doctor doctor) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_DOCTOR);
             PreparedStatement specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_TYPE);
        ) {
            specializationStatement.setString(1, doctor.getSpecialization());
            ResultSet resultSet = specializationStatement.executeQuery();
            Integer specializationID = null;
            if (resultSet.next()) {
                specializationID = resultSet.getInt("id");
            }
            statement.setInt(1, doctor.getId());
            statement.setString(2, doctor.getName());
            statement.setString(3, doctor.getSurname());
            statement.setInt(4, specializationID);
            statement.setInt(5, doctor.getWorkingShift().getId());

            statement.executeUpdate();
            logger.debug("Doctor with id={} was created", doctor.getId());
            return doctor.getId();
        } catch (SQLException e) {
            throw new PersistentException("It is impossible co connect to database", e);
        }
    }

    /**
     * Reads doctor with specified id
     *
     * @param id
     * @return found doctor
     * @throws PersistentException if database error occurs
     */
    @Override
    public Doctor read(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_DOCTOR_BY_ID);
             PreparedStatement specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_ID);
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Doctor doctor = null;
            if (resultSet.next()) {
                doctor = new Doctor();
                doctor.setId(id);
                doctor.setName(resultSet.getString("name"));
                doctor.setSurname(resultSet.getString("surname"));
                doctor.setWorkingShift(Shift.getById(resultSet.getInt("working_shift")));

                specializationStatement.setInt(1, resultSet.getInt("specialization_id"));
                resultSet = specializationStatement.executeQuery();
                if (resultSet.next()) {
                    doctor.setSpecialization(resultSet.getString("type"));
                }
            }
            logger.debug("Doctor was read");
            return doctor;
        } catch (SQLException e) {
            throw new PersistentException("It is impossible co connect to database", e);
        }
    }

    /**
     * Updates doctor
     *
     * @param doctor
     * @throws PersistentException if database error occurs
     */
    @Override
    public void update(Doctor doctor) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_DOCTOR);
             PreparedStatement specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_TYPE);
        ) {
            statement.setString(1, doctor.getName());
            statement.setString(2, doctor.getSurname());
            statement.setInt(4, doctor.getWorkingShift().getId());
            statement.setInt(5, doctor.getId());

            specializationStatement.setString(1, doctor.getSpecialization());
            ResultSet resultSet = specializationStatement.executeQuery();
            if (resultSet.next()) {
                statement.setInt(3, resultSet.getInt("id"));
            }
            statement.executeUpdate();
            logger.debug("Doctor with id={} was updated", doctor.getId());
        } catch (SQLException e) {
            throw new PersistentException("Doctor cannot be updated");
        }
    }

    /**
     * Deletes doctor with specified id
     *
     * @param id
     * @throws PersistentException if database error occurs
     */
    @Override
    public void delete(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_DOCTOR)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.debug("Doctor with id={} was deleted", id);
        } catch (SQLException e) {
            throw new PersistentException("Doctor cannot be deleted");
        }
    }

    /**
     * Reads all doctors
     *
     * @return list of found doctors
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Doctor> read() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_DOCTOR);
             PreparedStatement specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_ID);
        ) {
            ResultSet resultSet = statement.executeQuery();
            Doctor doctor;
            List<Doctor> doctors = new ArrayList<>();
            while (resultSet.next()) {
                doctor = new Doctor();
                doctor.setId(resultSet.getInt("id"));
                doctor.setName(resultSet.getString("name"));
                doctor.setSurname(resultSet.getString("surname"));
                doctor.setWorkingShift(Shift.getById(resultSet.getInt("working_shift")));

                specializationStatement.setInt(1, resultSet.getInt("specialization_id"));
                ResultSet resultSetSpecialization = specializationStatement.executeQuery();
                if (resultSetSpecialization.next()) {
                    doctor.setSpecialization(resultSetSpecialization.getString("type"));
                }
                doctors.add(doctor);
            }
            logger.debug("Doctors were read");
            return doctors;
        } catch (SQLException e) {
            throw new PersistentException("It is impossible to read doctors");
        }
    }

    /**
     * Reads all doctors without vacation at specified date
     *
     * @param date
     * @return list of doctors
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Doctor> readWithoutVacation(Date date) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_DOCTORS_WITHOUT_VACATION_DAY);
             PreparedStatement specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_ID);
        ) {
            statement.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet resultSet = statement.executeQuery();

            List<Doctor> doctors = new ArrayList<>();
            Doctor doctor = null;
            while (resultSet.next()) {
                doctor = new Doctor();
                doctor.setId(resultSet.getInt("id"));
                doctor.setName(resultSet.getString("name"));
                doctor.setSurname(resultSet.getString("surname"));
                doctor.setWorkingShift(Shift.getById(resultSet.getInt("working_shift")));

                specializationStatement.setInt(1, resultSet.getInt("specialization_id"));
                ResultSet resultSetSpecialization = specializationStatement.executeQuery();
                if (resultSetSpecialization.next()) {
                    doctor.setSpecialization(resultSetSpecialization.getString("type"));
                }
                doctors.add(doctor);
            }
            logger.debug("Doctors were read");
            return doctors;
        } catch (SQLException e) {
            throw new PersistentException("It is impossible to read doctors");
        }
    }

    /**
     * Reads all specializations
     *
     * @return list of specialization names
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<String> readSpecializations() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_SPECIALIZATIONS)) {
            ResultSet resultSet = statement.executeQuery();
            List<String> specializations = new ArrayList<>();
            while (resultSet.next()) {
                specializations.add(resultSet.getString("type"));
            }
            logger.debug("Specializations were read");
            return specializations;
        } catch (SQLException e) {
            throw new PersistentException("It is impossible to read specializations");
        }
    }

    /**
     * Reads doctors by specialization
     *
     * @param specialization
     * @return list of found doctors
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Doctor> readBySpecializationType(String specialization) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_DOCTOR_BY_SPECIALIZATION);
             PreparedStatement specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_TYPE);
        ) {
            specializationStatement.setString(1, specialization);
            ResultSet resultSet = specializationStatement.executeQuery();
            if (resultSet.next()) {
                statement.setInt(1, resultSet.getInt("id"));
            }

            resultSet = statement.executeQuery();
            List<Doctor> doctors = new ArrayList<>();
            Doctor doctor;
            while (resultSet.next()) {
                doctor = new Doctor();
                doctor.setId(resultSet.getInt("id"));
                doctor.setName(resultSet.getString("name"));
                doctor.setSurname(resultSet.getString("surname"));
                doctor.setSpecialization(specialization);
                doctor.setWorkingShift(Shift.getById(resultSet.getInt("working_shift")));
                doctors.add(doctor);
            }
            logger.debug("Doctors were read");
            return doctors;
        } catch (SQLException e) {
            throw new PersistentException("It is impossible to read doctors");
        }
    }

    /**
     * Reads doctor by surname and name
     *
     * @param surname
     * @param name
     * @return found doctor
     * @throws PersistentException if database error occurs
     */
    @Override
    public Doctor readBySurnameAndName(String surname, String name) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_DOCTOR_BY_SURNAME_AND_NAME);
             PreparedStatement specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_ID);
        ) {
            statement.setString(1, name);
            statement.setString(2, surname);

            ResultSet resultSet = statement.executeQuery();
            Doctor doctor = null;
            if (resultSet.next()) {
                doctor = new Doctor();
                doctor.setId(resultSet.getInt("id"));
                doctor.setName(resultSet.getString("name"));
                doctor.setSurname(resultSet.getString("surname"));
                doctor.setWorkingShift(Shift.getById(resultSet.getInt("working_shift")));

                specializationStatement.setInt(1, resultSet.getInt("specialization_id"));
                ResultSet resultSetSpecialization = specializationStatement.executeQuery();
                if (resultSetSpecialization.next()) {
                    doctor.setSpecialization(resultSetSpecialization.getString("type"));
                }
            }
            logger.debug("Doctors were read");
            return doctor;
        } catch (SQLException e) {
            throw new PersistentException("It is impossible to read doctors");
        }
    }
}
