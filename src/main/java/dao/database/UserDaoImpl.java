package dao.database;

import dao.UserDao;
import domain.User;
import domain.enumeration.Role;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {
    private static final String CREATE_USER = "INSERT INTO `user` (`login`, `password`, `role`)" +
            " VALUES (?, ?, ?)";

    private static final String READ_USER = "SELECT `login`, `password`, `role` " +
            "FROM `user` WHERE `id` = ?";
    private static final String READ_USER_BY_PASSWORD_AND_LOGIN =
            "SELECT `id`, `role` FROM `user` WHERE `login` = ? AND `password` = ?";

    private static final String UPDATE_USER = "UPDATE `user` SET `login` = ?, `password` = ?," +
            " `role` = ? WHERE `id` = ?";

    private static final String DELETE_USER = "DELETE FROM `user` WHERE `id` = ?";
    private final Logger logger = LogManager.getLogger(getClass().getName());

    @Override
    public Integer create(User user) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {

            statement = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole().getId());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                logger.error("There is no autoincremented index after trying to add record into table `user`");
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
    public User read(Integer id) throws PersistentException {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_USER);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(id);
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.getById(resultSet.getInt("role")));
            }
            return user;
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
    public void update(User user) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_USER);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole().getId());
            statement.setInt(4, user.getId());
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
            statement = connection.prepareStatement(DELETE_USER);
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
    public User read(String login, String password) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_USER_BY_PASSWORD_AND_LOGIN);
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(login);
                user.setPassword(password);
                user.setRole(Role.getById(resultSet.getInt("role")));
            }
            return user;
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
