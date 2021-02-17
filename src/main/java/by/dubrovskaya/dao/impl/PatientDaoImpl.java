package by.dubrovskaya.dao.impl;

import by.dubrovskaya.dao.PatientDao;
import by.dubrovskaya.dao.extractor.Extractor;
import by.dubrovskaya.dao.extractor.PatientExtractor;
import by.dubrovskaya.domain.Patient;
import by.dubrovskaya.domain.User;
import by.dubrovskaya.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientDaoImpl extends BaseDaoImpl implements PatientDao {
    private final Logger logger = LogManager.getLogger(getClass().getName());
    private final Extractor<Patient> patientExtractor;

    public PatientDaoImpl() {
        patientExtractor = new PatientExtractor();
    }

    private static final String NAME = "name";

    private static final String CREATE_PATIENT =
            "INSERT INTO `patient`(`id`,`name`, `surname`, `email`, `phone_number`, `address`) " +
                    "VALUES (?,?,?,?,?,?)";

    private static final String CREATE_PATIENT_DISEASE =
            "INSERT INTO `patient_disease`(`patient_id`,`disease_id`, `appointment_id`) " +
                    "VALUES (?,?,?)";

    private static final String READ_PATIENTS = "SELECT `id`, `name`, `surname`, `email`, `phone_number`," +
            " `address` FROM `patient`";

    private static final String READ_PATIENT_BY_ID = READ_PATIENTS + " WHERE `id`=? ";

    private static final String READ_PATIENT_BY_EMAIL = READ_PATIENTS + " WHERE `email`=?";

    private static final String READ_DISEASES = "SELECT `id`, `name` FROM `disease`";

    private static final String READ_DISEASE_BY_NAME = READ_DISEASES + " WHERE `name`=?";

    private static final String READ_PATIENTS_LIMIT = "SELECT SQL_CALC_FOUND_ROWS `id`, `name`, `surname`, `email`, " +
            "`phone_number`,`address` FROM `patient` LIMIT ? ,?";

    private static final String SQL_NUMBER_OF_RECORDS = "SELECT FOUND_ROWS()";

    private static final String READ_DISEASES_BY_PATIENT = "SELECT disease.name FROM disease JOIN patient_disease" +
            " ON disease.id = patient_disease.disease_id WHERE patient_id=?";

    private static final String READ_DISEASE_BY_PATIENT_AND_APPOINTMENT = READ_DISEASES_BY_PATIENT + " AND appointment_id=?";

    private static final String UPDATE_PATIENT = "UPDATE `patient` SET `name`=?, `surname`=?, `email`=?, `phone_number`=?," +
            " `address`=? WHERE `id` = ?";

    private static final String DELETE_PATIENT = "DELETE FROM `patient` WHERE `id` = ?";

    /**
     * Creates patient in database
     *
     * @param patient that should be created
     * @return generated patient's id
     * @throws PersistentException if database error occurs
     */
    @Override
    public Integer create(Patient patient) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_PATIENT)) {
            statement.setInt(1, patient.getId());
            statement.setString(2, patient.getName());
            statement.setString(3, patient.getSurname());
            statement.setString(4, patient.getEmail());
            statement.setString(5, patient.getPhoneNumber());
            statement.setString(6, patient.getAddress());

            statement.executeUpdate();
            logger.debug("Patient with id={} was created", patient.getId());
            return patient.getId();
        } catch (SQLException e) {
            throw new PersistentException("Patient wasn't created");
        }
    }

    /**
     * Reads all patients from database
     *
     * @return list of found patients
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Patient> read() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_PATIENTS);
             PreparedStatement diseasesStatement = connection.prepareStatement(READ_DISEASES_BY_PATIENT)
        ) {
            ResultSet resultSet = statement.executeQuery();
            Patient patient;
            List<Patient> patients = new ArrayList<>();
            while (resultSet.next()) {
                patient = patientExtractor.extract(resultSet);
                diseasesStatement.setInt(1, patient.getId());
                ResultSet diseaseResultSet = diseasesStatement.executeQuery();
                while (diseaseResultSet.next()) {
                    patient.getDiseases().add(diseaseResultSet.getString(NAME));
                }
                patients.add(patient);
            }
            logger.debug("Patients were read");
            return patients;
        } catch (SQLException e) {
            throw new PersistentException("Patients were not read");
        }
    }

    /**
     * Reads patient by id
     *
     * @param id unique identifier
     * @return found patient
     * @throws PersistentException if database error occurs
     */
    @Override
    public Patient read(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_PATIENT_BY_ID);
             PreparedStatement diseasesStatement = connection.prepareStatement(READ_DISEASES_BY_PATIENT)
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Patient patient = null;
            if (resultSet.next()) {
                patient = patientExtractor.extract(resultSet);
                diseasesStatement.setInt(1, patient.getId());
                ResultSet diseaseResultSet = diseasesStatement.executeQuery();

                while (diseaseResultSet.next()) {
                    patient.getDiseases().add(diseaseResultSet.getString(NAME));
                }
            }
            logger.debug("Patient with id = {} was read", id);
            return patient;
        } catch (SQLException e) {
            throw new PersistentException("Patient wasn't read");
        }
    }

    /**
     * Updates patient
     *
     * @param patient that should be updated
     * @throws PersistentException if database error occurs
     */
    @Override
    public void update(Patient patient) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PATIENT)) {
            statement.setString(1, patient.getName());
            statement.setString(2, patient.getSurname());
            statement.setString(3, patient.getEmail());
            statement.setString(4, patient.getPhoneNumber());
            statement.setString(5, patient.getAddress());
            statement.setInt(6, patient.getId());

            statement.executeUpdate();
            logger.debug("Patient with id={} was updated", patient.getId());
        } catch (SQLException e) {
            throw new PersistentException("Patient wasn't updated");
        }
    }

    /**
     * Deletes patient by id
     *
     * @param id unique identifier
     * @throws PersistentException if database error occurs
     */
    @Override
    public void delete(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PATIENT)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.debug("Patient with id = {} was deleted", id);
        } catch (SQLException e) {
            throw new PersistentException("Patient wasn't deleted");
        }
    }

    /**
     * Reads patient by email
     *
     * @param email
     * @return found patient
     * @throws PersistentException if database error occurs
     */
    @Override
    public Patient readByEmail(String email) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_PATIENT_BY_EMAIL);
             PreparedStatement diseaseStatement = connection.prepareStatement(READ_DISEASES_BY_PATIENT)
        ) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            Patient patient = null;
            if (resultSet.next()) {
                patient = patientExtractor.extract(resultSet);

                diseaseStatement.setInt(1, patient.getId());
                ResultSet diseaseResultSet = diseaseStatement.executeQuery();
                while (diseaseResultSet.next()) {
                    patient.getDiseases().add(diseaseResultSet.getString(NAME));
                }
            }
            logger.debug("Patient with email = {} was read", email);
            return patient;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * Reads all diseases
     *
     * @return list of diseases' names
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<String> readDiseases() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_DISEASES)) {
            ResultSet resultSet = statement.executeQuery();
            List<String> diseases = new ArrayList<>();
            while (resultSet.next()) {
                diseases.add(resultSet.getString(NAME));
            }
            logger.debug("Diseases were read");
            return diseases;
        } catch (SQLException e) {
            throw new PersistentException("It is impossible to read diseases");
        }
    }

    /**
     * Reads names of diseases by patient id
     *
     * @param patientId patient's id
     * @return list of found patient's disease
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<String> readDiseasesByPatient(Integer patientId) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_DISEASES_BY_PATIENT)) {
            statement.setInt(1, patientId);
            ResultSet resultSet = statement.executeQuery();
            List<String> diseases = new ArrayList<>();
            while (resultSet.next()) {
                diseases.add(resultSet.getString(NAME));
            }
            logger.debug("Diseases were read");
            return diseases;
        } catch (SQLException e) {
            throw new PersistentException("It is impossible to read diseases");
        }
    }

    /**
     * Reads patient's disease established on appointment
     *
     * @param patientId     patient's id
     * @param appointmentId id of appointment
     * @return found disease
     * @throws PersistentException if database error occurs
     */
    @Override
    public String readDiseaseByAppointment(Integer patientId, Integer appointmentId) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_DISEASE_BY_PATIENT_AND_APPOINTMENT)) {
            statement.setInt(1, patientId);
            statement.setInt(2, appointmentId);
            ResultSet resultSet = statement.executeQuery();
            String disease = null;
            if (resultSet.next()) {
                disease = resultSet.getString(NAME);
            }
            logger.debug("Disease was read");
            return disease;
        } catch (SQLException e) {
            throw new PersistentException("It is impossible to read disease");
        }
    }

    /**
     * Reads patients with specified offset number of records
     *
     * @param offset      starting from
     * @param noOfRecords amount of patients
     * @return Map<K, V> , K- number of found rows, V- list of patients with offset
     * @throws PersistentException if database error occurs
     */
    @Override
    public Map<Integer, List<Patient>> read(int offset, int noOfRecords) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_PATIENTS_LIMIT)) {
            statement.setInt(1, offset);
            statement.setInt(2, noOfRecords);
            ResultSet resultSet = statement.executeQuery();

            Patient patient;
            Map<Integer, List<Patient>> map = new HashMap<>();
            List<Patient> patients = new ArrayList<>();
            while (resultSet.next()) {
                patient = patientExtractor.extract(resultSet);
                patients.add(patient);
            }
            resultSet = statement.executeQuery(SQL_NUMBER_OF_RECORDS);
            Integer sqlNoOfRecords = null;
            if (resultSet.next()) {
                sqlNoOfRecords = resultSet.getInt(1);
            }
            map.put(sqlNoOfRecords, patients);
            logger.debug("Patients were read");
            return map;
        } catch (SQLException e) {
            throw new PersistentException("Patients cannot be read", e);
        }
    }

    /**
     * Saves patient's disease defined on appointment
     *
     * @param patientId     patient's id
     * @param appointmentId id of appointment
     * @param disease       patient's disease
     * @throws PersistentException if database error occurs
     */
    @Override
    public void saveDiseaseForPatient(Integer patientId, Integer appointmentId, String disease) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_PATIENT_DISEASE);
             PreparedStatement diseaseStatement = connection.prepareStatement(READ_DISEASE_BY_NAME)
        ) {
            diseaseStatement.setString(1, disease);
            ResultSet resultSet = diseaseStatement.executeQuery();
            int diseaseID;
            if (resultSet.next()) {
                diseaseID = resultSet.getInt("id");
            } else {
                throw new PersistentException("Disease id not found");
            }
            statement.setInt(1, patientId);
            statement.setInt(2, diseaseID);
            statement.setInt(3, appointmentId);

            statement.executeUpdate();
            logger.debug("Patient's, whose id = {}, disease was created", patientId);
        } catch (SQLException e) {
            throw new PersistentException("Patient's disease wasn't created");
        }
    }
}
