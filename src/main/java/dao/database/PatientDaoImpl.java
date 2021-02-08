package dao.database;

import dao.PatientDao;
import domain.Patient;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDaoImpl extends BaseDaoImpl implements PatientDao {
    private final Logger logger = LogManager.getLogger(getClass().getName());
    private static final String CREATE_PATIENT =
            "INSERT INTO `patient`(`id`,`name`, `surname`, `email`, `phone_number`, `address`) " +
                    "VALUES (?,?,?,?,?,?)";

    private static final String CREATE_PATIENT_DISEASE =
            "INSERT INTO `patient_disease`(`patient_id`,`disease_id`, `appointment_id`) " +
                    "VALUES (?,?,?)";

    private static final String READ_PATIENT = "SELECT `name`, `surname`, `email`, `phone_number`," +
            " `address` FROM `patient` WHERE `id`=? ";

    private static final String READ_PATIENTS = "SELECT `id`, `name`, `surname`, `email`, `phone_number`," +
            " `address` FROM `patient`";


    private static final String READ_PATIENT_BY_EMAIL =
            "SELECT `id`, `name`, `surname`, `phone_number`, `address` FROM `patient`" +
                    " WHERE `email`=?  ";

    private static final String READ_DISEASES_BY_PATIENT = "SELECT disease.name FROM disease JOIN patient_disease" +
            " ON disease.id = patient_disease.disease_id WHERE patient_id=?";

    private static final String READ_DISEASE_BY_NAME = "SELECT `id` FROM `disease` " +
            "WHERE `name`=?";

    private static final String READ_DISEASE_BY_PATIENT_AND_APPOINTMENT = "SELECT disease.name FROM disease JOIN " +
            "patient_disease pd on disease.id = pd.disease_id WHERE patient_id=? AND appointment_id=?";

    private static final String UPDATE_PATIENT = "UPDATE `patient` " +
            "SET `name`=?, `surname`=?, `email`=?, `phone_number`=?, `address`=?" +
            " WHERE `id` = ?";

    private static final String DELETE_PATIENT = "DELETE FROM `patient` WHERE `id` = ?";

    private static final String READ_DISEASES = "SELECT `id`, `name` FROM `disease`";

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

    @Override
    public List<Patient> read() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_PATIENTS);
             PreparedStatement diseasesStatement = connection.prepareStatement(READ_DISEASES_BY_PATIENT);
        ) {
            ResultSet resultSet = statement.executeQuery();
            Patient patient;
            List<Patient> patients = new ArrayList<>();
            while (resultSet.next()) {
                patient = new Patient();
                patient.setId(resultSet.getInt("id"));
                patient.setName(resultSet.getString("name"));
                patient.setSurname(resultSet.getString("surname"));
                patient.setEmail(resultSet.getString("email"));
                patient.setPhoneNumber(resultSet.getString("phone_number"));
                patient.setAddress(resultSet.getString("address"));

                //TODO
                diseasesStatement.setInt(1, patient.getId());
                ResultSet diseaseResultSet = diseasesStatement.executeQuery();

                while (diseaseResultSet.next()) {
                    patient.getDiseases().add(diseaseResultSet.getString("name"));
                }
                patients.add(patient);
            }
            logger.debug("Patients were read");
            return patients;
        } catch (SQLException e) {
            throw new PersistentException("Patients were not read");
        }
    }

    @Override
    public Patient read(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_PATIENT);
             PreparedStatement diseasesStatement = connection.prepareStatement(READ_DISEASES_BY_PATIENT)
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Patient patient = null;
            if (resultSet.next()) {
                patient = new Patient();
                patient.setId(id);
                patient.setName(resultSet.getString("name"));
                patient.setSurname(resultSet.getString("surname"));
                patient.setEmail(resultSet.getString("email"));
                patient.setPhoneNumber(resultSet.getString("phone_number"));
                patient.setAddress(resultSet.getString("address"));

                diseasesStatement.setInt(1, patient.getId());
                ResultSet diseaseResultSet = diseasesStatement.executeQuery();

                while (diseaseResultSet.next()) {
                    patient.getDiseases().add(diseaseResultSet.getString("name"));
                }
            }
            logger.debug("Patient with id = {} was read", id);
            return patient;
        } catch (SQLException e) {
            throw new PersistentException("Patient wasn't read");
        }
    }

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

    @Override
    public Patient readByEmail(String email) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_PATIENT_BY_EMAIL);
             PreparedStatement diseaseStatement = connection.prepareStatement(READ_DISEASES_BY_PATIENT);
        ) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            Patient patient = null;
            if (resultSet.next()) {
                patient = new Patient();
                patient.setId(resultSet.getInt("id"));
                patient.setName(resultSet.getString("name"));
                patient.setSurname(resultSet.getString("surname"));
                patient.setEmail(email);
                patient.setPhoneNumber(resultSet.getString("phone_number"));
                patient.setAddress(resultSet.getString("address"));

                diseaseStatement.setInt(1, patient.getId());
                ResultSet diseaseResultSet = diseaseStatement.executeQuery();

                while (diseaseResultSet.next()) {
                    patient.getDiseases().add(diseaseResultSet.getString("name"));
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
                diseases.add(resultSet.getString("name"));
            }
            logger.debug("Diseases were read");
            return diseases;
        } catch (SQLException e) {
            throw new PersistentException("It is impossible to read diseases");
        }
    }

    @Override
    public List<String> readDiseasesByPatient(Integer patientId) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_DISEASES_BY_PATIENT)) {
            statement.setInt(1, patientId);
            ResultSet resultSet = statement.executeQuery();
            List<String> diseases = new ArrayList<>();
            while (resultSet.next()) {
                diseases.add(resultSet.getString("name"));
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
     * @param patientId
     * @param appointmentId
     * @return
     * @throws PersistentException
     */
    @Override
    public String readDiseaseByAppointment(Integer patientId, Integer appointmentId) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_DISEASE_BY_PATIENT_AND_APPOINTMENT)) {
            statement.setInt(1, patientId);
            statement.setInt(2, appointmentId);
            ResultSet resultSet = statement.executeQuery();
            String disease = null;
            if (resultSet.next()) {
                disease = resultSet.getString("name");
            }
            logger.debug("Disease was read");
            return disease;
        } catch (SQLException e) {
            throw new PersistentException("It is impossible to read disease");
        }
    }

    /**
     * Saves patient's disease defined on appointment
     *
     * @param patientId
     * @param appointmentId
     * @param disease
     * @throws PersistentException
     */
    @Override
    public void saveDiseaseForPatient(Integer patientId, Integer appointmentId, String disease) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_PATIENT_DISEASE);
             PreparedStatement diseaseStatement = connection.prepareStatement(READ_DISEASE_BY_NAME)
        ) {
            diseaseStatement.setString(1, disease);
            ResultSet resultSet = diseaseStatement.executeQuery();
            Integer diseaseID = null;
            if (resultSet.next()) {
                diseaseID = resultSet.getInt("id");
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
