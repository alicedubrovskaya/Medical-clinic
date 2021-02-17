package by.dubrovskaya.dao.impl;

import by.dubrovskaya.dao.DoctorDao;
import by.dubrovskaya.dao.extractor.DoctorExtractor;
import by.dubrovskaya.dao.extractor.Extractor;
import by.dubrovskaya.dao.extractor.UserExtractor;
import by.dubrovskaya.domain.Doctor;
import by.dubrovskaya.domain.Patient;
import by.dubrovskaya.domain.User;
import by.dubrovskaya.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Data access object for doctor
 */
public class DoctorDaoImpl extends BaseDaoImpl implements DoctorDao {
    private final Extractor<Doctor> doctorExtractor;
    private static final Logger logger = LogManager.getLogger(DoctorDaoImpl.class);


    public DoctorDaoImpl() {
        doctorExtractor = new DoctorExtractor();
    }

    private static final String ID = "id";
    private static final String SPECIALIZATION_ID = "specialization_id";
    private static final String TYPE = "type";
    private static final String SUCCESSFUL_READING_OF_DOCTORS = "Doctors were read";
    private static final String UNSUCCESSFUL_READING_OF_DOCTORS = "It is impossible to read doctors";

    private static final String CREATE_DOCTOR = "INSERT INTO `doctor` (`id`, `name`, `surname`," +
            " `specialization_id`, `working_shift`) VALUES (?,?,?,?,?)";

    private static final String READ_DOCTOR = "SELECT `id`, `name`, `surname`, `specialization_id`," +
            "`working_shift` FROM `doctor`";

    private static final String READ_DOCTOR_BY_ID = READ_DOCTOR + " WHERE id=?";

    private static final String READ_DOCTORS_WITHOUT_VACATION_DAY = "SELECT * from `doctor` LEFT JOIN vacation v " +
            "ON doctor.id = v.doctor_id WHERE doctor_id is NULL OR (? NOT BETWEEN `start` AND `end`)";

    private static final String READ_DOCTOR_BY_SURNAME_AND_NAME = READ_DOCTOR + " WHERE name=? AND surname=?";

    private static final String READ_DOCTOR_BY_SPECIALIZATION = READ_DOCTOR + " WHERE specialization_id=?";

    private static final String READ_DOCTORS_LIMIT = "SELECT SQL_CALC_FOUND_ROWS `id`, `name`, `surname`, `specialization_id`," +
            "`working_shift` FROM `doctor` LIMIT ? ,?";

    private static final String SQL_NUMBER_OF_RECORDS = "SELECT FOUND_ROWS()";

    private static final String READ_SPECIALIZATIONS = "SELECT `id`, `type` FROM specialization";

    private static final String READ_SPECIALIZATION_BY_TYPE = READ_SPECIALIZATIONS + " WHERE `type`=?";

    private static final String READ_SPECIALIZATION_BY_ID = READ_SPECIALIZATIONS + " WHERE `id`=?";

    private static final String UPDATE_DOCTOR = "UPDATE `doctor` " +
            "SET `name`=?, `surname`=?, `specialization_id`=?, `working_shift`=? WHERE `id` = ?";

    private static final String DELETE_DOCTOR = "DELETE FROM `doctor` WHERE `id` = ?";

    /**
     * Creates doctor in database
     *
     * @param doctor that should be created
     * @return generated key
     * @throws PersistentException if database error occurs
     */
    @Override
    public Integer create(Doctor doctor) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_DOCTOR);
             PreparedStatement specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_TYPE)
        ) {
            specializationStatement.setString(1, doctor.getSpecialization());
            ResultSet resultSet = specializationStatement.executeQuery();
            Integer specializationID = null;
            if (resultSet.next()) {
                specializationID = resultSet.getInt(ID);
            }
            statement.setInt(1, doctor.getId());
            statement.setString(2, doctor.getName());
            statement.setString(3, doctor.getSurname());
            if (specializationID != null) {
                statement.setInt(4, specializationID);
            } else {
                throw new PersistentException("Specialization id is null");
            }
            statement.setInt(5, doctor.getWorkingShift().getId());

            statement.executeUpdate();
            logger.debug("Doctor with id = {} was created", doctor.getId());
            return doctor.getId();
        } catch (SQLException e) {
            throw new PersistentException("Doctor wasn't created", e);
        }
    }

    /**
     * Reads doctor with specified id
     *
     * @param id unique identifier
     * @return found doctor
     * @throws PersistentException if database error occurs
     */
    @Override
    public Doctor read(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_DOCTOR_BY_ID);
             PreparedStatement specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_ID)
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Doctor doctor = null;
            if (resultSet.next()) {
                doctor = doctorExtractor.extract(resultSet);
                specializationStatement.setInt(1, resultSet.getInt(SPECIALIZATION_ID));
                resultSet = specializationStatement.executeQuery();
                if (resultSet.next()) {
                    doctor.setSpecialization(resultSet.getString(TYPE));
                }
            }
            logger.debug("Doctor was read");
            return doctor;
        } catch (SQLException e) {
            throw new PersistentException("Doctor wasn't read", e);
        }
    }

    /**
     * Updates doctor
     *
     * @param doctor that should updated
     * @throws PersistentException if database error occurs
     */
    @Override
    public void update(Doctor doctor) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_DOCTOR);
             PreparedStatement specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_TYPE)
        ) {
            statement.setString(1, doctor.getName());
            statement.setString(2, doctor.getSurname());
            statement.setInt(4, doctor.getWorkingShift().getId());
            statement.setInt(5, doctor.getId());

            specializationStatement.setString(1, doctor.getSpecialization());
            ResultSet resultSet = specializationStatement.executeQuery();
            if (resultSet.next()) {
                statement.setInt(3, resultSet.getInt(ID));
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
     * @param id unique identifier
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
             PreparedStatement specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_ID)
        ) {
            ResultSet resultSet = statement.executeQuery();
            Doctor doctor;
            List<Doctor> doctors = new ArrayList<>();
            while (resultSet.next()) {
                doctor = doctorExtractor.extract(resultSet);
                specializationStatement.setInt(1, resultSet.getInt(SPECIALIZATION_ID));
                ResultSet resultSetSpecialization = specializationStatement.executeQuery();
                if (resultSetSpecialization.next()) {
                    doctor.setSpecialization(resultSetSpecialization.getString(TYPE));
                }
                doctors.add(doctor);
            }
            logger.debug(SUCCESSFUL_READING_OF_DOCTORS);
            return doctors;
        } catch (SQLException e) {
            throw new PersistentException(UNSUCCESSFUL_READING_OF_DOCTORS);
        }
    }

    /**
     * Reads all doctors without vacation at specified date
     *
     * @param date of vacation
     * @return list of doctors
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Doctor> readWithoutVacation(Date date) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_DOCTORS_WITHOUT_VACATION_DAY);
             PreparedStatement specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_ID)
        ) {
            statement.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet resultSet = statement.executeQuery();

            List<Doctor> doctors = new ArrayList<>();
            Doctor doctor;
            while (resultSet.next()) {
                doctor = doctorExtractor.extract(resultSet);
                specializationStatement.setInt(1, resultSet.getInt(SPECIALIZATION_ID));
                ResultSet resultSetSpecialization = specializationStatement.executeQuery();
                if (resultSetSpecialization.next()) {
                    doctor.setSpecialization(resultSetSpecialization.getString(TYPE));
                }
                doctors.add(doctor);
            }
            logger.debug(SUCCESSFUL_READING_OF_DOCTORS);
            return doctors;
        } catch (SQLException e) {
            throw new PersistentException(UNSUCCESSFUL_READING_OF_DOCTORS);
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
                specializations.add(resultSet.getString(TYPE));
            }
            logger.debug("Specializations were read");
            return specializations;
        } catch (SQLException e) {
            throw new PersistentException("It is impossible to read specializations");
        }
    }

    /**
     * Reads doctors with specified offset number of records
     *
     * @param offset      starting from
     * @param noOfRecords amount of doctors
     * @return Map<K, V> , K- number of found rows, V- list of doctors with offset
     * @throws PersistentException if database error occurs
     */
    @Override
    public Map<Integer, List<Doctor>> read(int offset, int noOfRecords) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_DOCTORS_LIMIT);
             PreparedStatement specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_ID)) {
            statement.setInt(1, offset);
            statement.setInt(2, noOfRecords);
            ResultSet resultSet = statement.executeQuery();

            Doctor doctor;
            Map<Integer, List<Doctor>> map = new HashMap<>();
            List<Doctor> doctors = new ArrayList<>();
            while (resultSet.next()) {
                doctor = doctorExtractor.extract(resultSet);
                specializationStatement.setInt(1, resultSet.getInt(SPECIALIZATION_ID));
                ResultSet resultSetSpecialization = specializationStatement.executeQuery();
                if (resultSetSpecialization.next()) {
                    doctor.setSpecialization(resultSetSpecialization.getString(TYPE));
                }
                doctors.add(doctor);
            }
            resultSet = statement.executeQuery(SQL_NUMBER_OF_RECORDS);
            Integer sqlNoOfRecords = null;
            if (resultSet.next()) {
                sqlNoOfRecords = resultSet.getInt(1);
            }
            map.put(sqlNoOfRecords, doctors);
            logger.debug("Doctors were read");
            return map;
        } catch (SQLException e) {
            throw new PersistentException("Doctors cannot be read", e);
        }
    }


    /**
     * Reads doctors by specialization
     *
     * @param specialization that has doctor
     * @return list of found doctors
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Doctor> readBySpecializationType(String specialization) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_DOCTOR_BY_SPECIALIZATION);
             PreparedStatement specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_TYPE)
        ) {
            specializationStatement.setString(1, specialization);
            ResultSet resultSet = specializationStatement.executeQuery();
            if (resultSet.next()) {
                statement.setInt(1, resultSet.getInt(ID));
            }

            resultSet = statement.executeQuery();
            List<Doctor> doctors = new ArrayList<>();
            Doctor doctor;
            while (resultSet.next()) {
                doctor = doctorExtractor.extract(resultSet);
                doctor.setSpecialization(specialization);
                doctors.add(doctor);
            }
            logger.debug(SUCCESSFUL_READING_OF_DOCTORS);
            return doctors;
        } catch (SQLException e) {
            throw new PersistentException(UNSUCCESSFUL_READING_OF_DOCTORS);
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
             PreparedStatement specializationStatement = connection.prepareStatement(READ_SPECIALIZATION_BY_ID)
        ) {
            statement.setString(1, name);
            statement.setString(2, surname);

            ResultSet resultSet = statement.executeQuery();
            Doctor doctor = null;
            if (resultSet.next()) {
                doctor = doctorExtractor.extract(resultSet);
                specializationStatement.setInt(1, resultSet.getInt(SPECIALIZATION_ID));
                ResultSet resultSetSpecialization = specializationStatement.executeQuery();
                if (resultSetSpecialization.next()) {
                    doctor.setSpecialization(resultSetSpecialization.getString(TYPE));
                }
            }
            logger.debug(SUCCESSFUL_READING_OF_DOCTORS);
            return doctor;
        } catch (SQLException e) {
            throw new PersistentException(UNSUCCESSFUL_READING_OF_DOCTORS);
        }
    }
}
