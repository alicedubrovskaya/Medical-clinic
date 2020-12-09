package dao.database;

import dao.ConnectorDB;
import dao.MedicalCardDao;
import domain.MedicalCard;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class MedicalCardDaoImpl extends BaseDaoImpl implements MedicalCardDao {
    private final Logger logger = LogManager.getLogger(getClass().getName());
    private static final String CREATE_MEDICAL_CARD = "INSERT INTO `medical_card`" +
            " (`chronic_diseases`, `vaccinations`) VALUES (?, ?)";
    private static final String READ_MEDICAL_CARD = "SELECT `chronic_diseases`, `vaccinations` " +
            "FROM `medical_card` WHERE id=?";
    private static final String UPDATE_MEDICAL_CARD = "UPDATE `medical_card` SET `chronic_diseases`=?," +
            "`vaccinations`=? WHERE id = ?";
    private static final String DELETE_MEDICAL_CARD = "DELETE FROM `medical_card` WHERE id=?";

    public MedicalCardDaoImpl() {
        this.connector = new ConnectorDB();
    }

    @Override
    public Integer create(MedicalCard medicalCard) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = connector.getConnection();
            statement = connection.prepareStatement(CREATE_MEDICAL_CARD, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, medicalCard.getChronicDiseases());
            statement.setString(2, medicalCard.getVaccinations());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                logger.error("There is no autoincremented index after trying to add record into table `medical_card`");
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
    public MedicalCard read(Integer id) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connector.getPreparedStatement(READ_MEDICAL_CARD);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            MedicalCard medicalCard = null;
            if (resultSet.next()) {
                medicalCard = new MedicalCard();
                medicalCard.setId(id);
                medicalCard.setChronicDiseases(resultSet.getString("chronic_diseases"));
                medicalCard.setVaccinations(resultSet.getString("vaccinations"));
            }
            return medicalCard;
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
    public void update(MedicalCard medicalCard) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connector.getPreparedStatement(UPDATE_MEDICAL_CARD);
            statement.setString(1, medicalCard.getChronicDiseases());
            statement.setString(2, medicalCard.getVaccinations());
            statement.setInt(3, medicalCard.getId());
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
            statement = connector.getPreparedStatement(DELETE_MEDICAL_CARD);
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
}
