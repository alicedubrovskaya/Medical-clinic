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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {
    private static final String CREATE_USER = "INSERT INTO `user` (`login`, `password`, `role`)" +
            " VALUES (?, ?, ?)";

    private static final String READ_USER = "SELECT `login`, `password`, `role` " +
            "FROM `user` WHERE `id` = ?";

    private static final String READ_USERS = "SELECT `id`, `login`, `password`, `role`" +
            "FROM `user`";
    private static final String READ_USER_BY_PASSWORD_AND_LOGIN =
            "SELECT `id`, `role` FROM `user` WHERE `login` = ? AND `password` = ?";

    private static final String READ_USER_BY_LOGIN =
            "SELECT `id`, `role`, `password` FROM `user` WHERE `login` = ?";

    private static final String READ_USERS_LIMIT = "SELECT SQL_CALC_FOUND_ROWS `id`,`login`, `password`, `role` FROM `user` LIMIT ? ,?";
    private static final String SQL_NUMBER_OF_RECORDS = "SELECT FOUND_ROWS()";


    private static final String UPDATE_USER = "UPDATE `user` SET `login` = ?, `password` = ?," +
            " `role` = ? WHERE `id` = ?";

    private static final String DELETE_USER = "DELETE FROM `user` WHERE `id` = ?";
    private final Logger logger = LogManager.getLogger(getClass().getName());

    /**
     * Creates user in database
     *
     * @param user
     * @return generated id
     * @throws PersistentException
     */
    @Override
    public Integer create(User user) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            Integer userId = null;
            if (resultSet.next()) {
                userId = resultSet.getInt(1);
                logger.debug("User with id={} was created", userId);
            } else {
                throw new PersistentException("There is no autoincremented id after trying to add record into table `user`");
            }
            return userId;
        } catch (SQLException e) {
            throw new PersistentException("User wasn't created");
        }
    }

    /**
     * Reads user from database
     *
     * @param id
     * @return found user
     * @throws PersistentException
     */
    @Override
    public User read(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_USER)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(id);
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.getById(resultSet.getInt("role")));
            }
            logger.debug("User was read");
            return user;
        } catch (SQLException e) {
            throw new PersistentException("User cannot be read");
        }
    }

    /**
     * Updates user in database
     *
     * @param user
     * @throws PersistentException
     */
    @Override
    public void update(User user) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole().getId());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
            logger.debug("User with id={} was updated", user.getId());
        } catch (SQLException e) {
            throw new PersistentException("User cannot be updated");
        }
    }

    /**
     * Deletes user by id
     *
     * @param id
     * @throws PersistentException
     */
    @Override
    public void delete(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.debug("User with id={} was deleted", id);
        } catch (SQLException e) {
            throw new PersistentException("User cannot be deleted");
        }
    }

    /**
     * Reads all users from database
     *
     * @return
     * @throws PersistentException
     */
    @Override
    public List<User> read() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_USERS)) {
            ResultSet resultSet = statement.executeQuery();
            User user;
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.getById(resultSet.getInt("role")));

                users.add(user);
            }
            logger.debug("Users were read");
            return users;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * Reads user by login with password
     *
     * @param login
     * @param password
     * @return found user
     * @throws PersistentException
     */
    @Override
    public User read(String login, String password) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_USER_BY_PASSWORD_AND_LOGIN)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(login);
                user.setPassword(password);
                user.setRole(Role.getById(resultSet.getInt("role")));
            }
            logger.debug("User was read");
            return user;
        } catch (SQLException e) {
            throw new PersistentException("User cannot be read");
        }
    }

    /**
     * Reads user by login form database
     *
     * @param login
     * @return found user
     * @throws PersistentException
     */
    @Override
    public User read(String login) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_USER_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(login);
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.getById(resultSet.getInt("role")));
                logger.debug("User with login={} was read", login);
            }
            return user;
        } catch (SQLException e) {
            throw new PersistentException("User cannot be read");
        }
    }

    /**
     * Reads users with specified offset number of records
     *
     * @param offset
     * @param noOfRecords
     * @return Map<K, V> , K- number of found rows, V- list of users with offset
     * @throws PersistentException
     */
    @Override
    public Map<Integer, List<User>> read(int offset, int noOfRecords) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_USERS_LIMIT)) {
            statement.setInt(1, offset);
            statement.setInt(2, noOfRecords);
            ResultSet resultSet = statement.executeQuery();

            User user;
            Map<Integer, List<User>> map = new HashMap<>();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.getById(resultSet.getInt("role")));
                users.add(user);
            }
            resultSet = statement.executeQuery(SQL_NUMBER_OF_RECORDS);
            Integer sqlNoOfRecords = null;
            if (resultSet.next()) {
                sqlNoOfRecords = resultSet.getInt(1);
            }
            map.put(sqlNoOfRecords, users);
            logger.debug("Users were read");
            return map;
        } catch (SQLException e) {
            throw new PersistentException("Users cannot be read", e);
        }
    }
}
