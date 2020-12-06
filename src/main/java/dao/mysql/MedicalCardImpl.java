package dao.mysql;

import dao.MedicalCardDao;
import domain.MedicalCard;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MedicalCardImpl extends BaseDaoImpl implements MedicalCardDao {
    private final Logger logger = LogManager.getLogger(getClass().getName());

    @Override
    public Integer create(MedicalCard medicalCard) throws PersistentException {
        String sql = "INSERT INTO `medical_card` (`chronic_diseases`, `vaccinations`) " +
                "VALUES (?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
        return null;
    }

    @Override
    public void update(MedicalCard medicalCard) throws PersistentException {

    }

    @Override
    public void delete(Integer id) throws PersistentException {

    }
}
