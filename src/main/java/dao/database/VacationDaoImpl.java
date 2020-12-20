package dao.database;

import dao.VacationDao;
import domain.Vacation;
import exception.PersistentException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VacationDaoImpl extends BaseDaoImpl implements VacationDao {

    private static final String CREATE_VACATION = "INSERT INTO `vacation`(`doctor_id`, `start`, `end`) VALUES (?,?,?)";

    private static final String READ_VACATION = "SELECT `start`, `end` FROM vacation WHERE `doctor_id`=?";

    private static final String READ_VACATION_BY_TIME = "SELECT * FROM vacation WHERE ? BETWEEN `start` and `end`";

    private static final String READ_VACATION_FOR_ALL = "SELECT * FROM holiday WHERE `day` = ? ";

    private static final String UPDATE_VACATION = "UPDATE `vacation` SET `start`=?, `end`=? WHERE `doctor_id`=?";

    private static final String DELETE_VACATION = "DELETE FROM `vacation` WHERE `doctor_id` = ?";

    @Override
    public Integer create(Vacation vacation) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(CREATE_VACATION);
            statement.setInt(1, vacation.getId());
            statement.setDate(2, new java.sql.Date(vacation.getStart().getTime()));
            statement.setDate(3, new java.sql.Date(vacation.getEnd().getTime()));
            statement.executeUpdate();

            return vacation.getId();
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
    public Vacation read(Integer id) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_VACATION);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Vacation vacation = null;
            if (resultSet.next()) {
                vacation = new Vacation();
                vacation.setId(id);
                vacation.setStart(resultSet.getDate("start"));
                vacation.setEnd(resultSet.getDate("end"));
            }
            return vacation;
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
    public void update(Vacation vacation) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_VACATION);
            statement.setDate(1, new java.sql.Date(vacation.getStart().getTime()));
            statement.setDate(2, new java.sql.Date(vacation.getEnd().getTime()));
            statement.setInt(3, vacation.getId());

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
            statement = connection.prepareStatement(DELETE_VACATION);
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
    public List<Vacation> readByTime(Date date) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            List<Vacation> vacations = new ArrayList<>();
            statement = connection.prepareStatement(READ_VACATION_BY_TIME);
            statement.setDate(1, new java.sql.Date(date.getTime()));
            resultSet = statement.executeQuery();
            Vacation vacation = null;
            while (resultSet.next()) {
                vacation = new Vacation();
                vacation.setId(resultSet.getInt("doctor_id"));
                vacation.setStart(resultSet.getDate("start"));
                vacation.setEnd(resultSet.getDate("end"));
                vacations.add(vacation);
            }
            return vacations;
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
    public Vacation readBySpecifiedDate(Date date) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_VACATION_FOR_ALL);
            statement.setDate(1, new java.sql.Date(date.getTime()));
            resultSet = statement.executeQuery();

            Vacation vacation = null;
            if (resultSet.next()) {
                vacation = new Vacation();
                vacation.setStart(resultSet.getDate("day"));
                vacation.setEnd(resultSet.getDate("day"));
            }
            return vacation;
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }
}
