package dao.database;

import dao.VacationDao;
import domain.Vacation;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.impl.DoctorServiceImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VacationDaoImpl extends BaseDaoImpl implements VacationDao {
    private static final Logger logger = LogManager.getLogger(VacationDaoImpl.class);

    private static final String CREATE_VACATION = "INSERT INTO `vacation`(`doctor_id`, `start`, `end`) VALUES (?,?,?)";

    private static final String READ_VACATION = "SELECT `start`, `end` FROM vacation WHERE `doctor_id`=?";

    private static final String READ_VACATIONS = "SELECT `doctor_id`, `start`, `end` FROM vacation";


    private static final String READ_VACATION_BY_TIME = "SELECT * FROM vacation WHERE ? BETWEEN `start` and `end`";

    private static final String READ_VACATION_FOR_ALL = "SELECT * FROM holiday WHERE `day` = ? ";

    private static final String UPDATE_VACATION = "UPDATE `vacation` SET `start`=?, `end`=? WHERE `doctor_id`=?";

    private static final String DELETE_VACATION = "DELETE FROM `vacation` WHERE `doctor_id` = ?";

    @Override
    public Integer create(Vacation vacation) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_VACATION);
        ) {
            statement.setInt(1, vacation.getId());
            statement.setDate(2, new java.sql.Date(vacation.getStart().getTime()));
            statement.setDate(3, new java.sql.Date(vacation.getEnd().getTime()));
            statement.executeUpdate();
            logger.debug("Vacation with id={} was created", vacation.getId());
            return vacation.getId();
        } catch (SQLException e) {
            throw new PersistentException("Vacation cannot be created");
        }
    }

    @Override
    public Vacation read(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_VACATION)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Vacation vacation = null;
            if (resultSet.next()) {
                vacation = new Vacation();
                vacation.setId(id);
                vacation.setStart(resultSet.getDate("start"));
                vacation.setEnd(resultSet.getDate("end"));
            }
            logger.debug("Vacation was read");
            return vacation;
        } catch (SQLException e) {
            throw new PersistentException("Vacation cannot be read");
        }
    }

    @Override
    public void update(Vacation vacation) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_VACATION)) {
            statement.setDate(1, new java.sql.Date(vacation.getStart().getTime()));
            statement.setDate(2, new java.sql.Date(vacation.getEnd().getTime()));
            statement.setInt(3, vacation.getId());

            statement.executeUpdate();
            logger.debug("Vacation with id={} was updated", vacation.getId());
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_VACATION);
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.debug("Vacation with id={} was deleted", id);
        } catch (SQLException e) {
            throw new PersistentException("Vacation cannot be deleted");
        }
    }

    @Override
    public List<Vacation> read() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_VACATIONS)) {
            List<Vacation> vacations = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            Vacation vacation = null;
            while (resultSet.next()) {
                vacation = new Vacation();
                vacation.setId(resultSet.getInt("doctor_id"));
                vacation.setStart(resultSet.getDate("start"));
                vacation.setEnd(resultSet.getDate("end"));
                vacations.add(vacation);
            }
            logger.debug("Vacations were read");
            return vacations;
        } catch (SQLException e) {
            throw new PersistentException("Vacations cannot be read");
        }
    }

    @Override
    public List<Vacation> readByTime(Date date) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_VACATION_BY_TIME)) {
            List<Vacation> vacations = new ArrayList<>();
            statement.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet resultSet = statement.executeQuery();
            Vacation vacation = null;
            while (resultSet.next()) {
                vacation = new Vacation();
                vacation.setId(resultSet.getInt("doctor_id"));
                vacation.setStart(resultSet.getDate("start"));
                vacation.setEnd(resultSet.getDate("end"));
                vacations.add(vacation);
            }
            logger.debug("Vacations were read");
            return vacations;
        } catch (SQLException e) {
            throw new PersistentException("Vacations cannot be read");
        }
    }

    @Override
    public Vacation readBySpecifiedDate(Date date) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_VACATION_FOR_ALL)) {
            statement.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet resultSet = statement.executeQuery();

            Vacation vacation = null;
            if (resultSet.next()) {
                vacation = new Vacation();
                vacation.setStart(resultSet.getDate("day"));
                vacation.setEnd(resultSet.getDate("day"));
            }
            logger.debug("Vacation was read");
            return vacation;
        } catch (SQLException e) {
            throw new PersistentException("Vacation cannot be read");
        }
    }
}
