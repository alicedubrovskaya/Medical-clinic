package service.impl;

import dao.UserDao;
import domain.User;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.PasswordEncryption;
import service.UserService;
import service.exception.ServicePersistentException;

import java.util.List;
import java.util.Map;

public class UserServiceImpl extends ServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    /**
     * Saves user
     *
     * @param user
     * @throws PersistentException
     */
    @Override
    public void save(User user) throws ServicePersistentException {
        UserDao userDao = transaction.createUserDao();
        try {
            if (user.getId() == null) {
                user.setPassword(PasswordEncryption.encrypt(user.getPassword()));
                user.setId(userDao.create(user));
            } else {
                if (user.getPassword() != null) {
                    user.setPassword(PasswordEncryption.encrypt(user.getPassword()));
                } else {
                    User existingUser = userDao.read(user.getId());
                    user.setPassword(existingUser.getPassword());
                }
                userDao.update(user);
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Deletes user by id
     *
     * @param id
     * @throws ServicePersistentException
     */
    @Override
    public void delete(Integer id) throws ServicePersistentException {
        UserDao userDao = transaction.createUserDao();
        try {
            userDao.delete(id);
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Finds user by login and password
     *
     * @param login
     * @param password
     * @return found user
     * @throws ServicePersistentException
     */
    @Override
    public User findByLoginAndPassword(String login, String password) throws ServicePersistentException {
        UserDao userDao = transaction.createUserDao();
        try {
            User user = userDao.read(login);
            if (user != null) {
                if (PasswordEncryption.checkPassword(password, user.getPassword())) {
                    return user;
                } else {
                    throw new ServicePersistentException("There is no user with such password");
                }
            } else {
                throw new ServicePersistentException("User with such login wasn't found");
            }

        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Finds user by login
     *
     * @param login
     * @return found user
     * @throws ServicePersistentException
     */
    @Override
    public User findByLogin(String login) throws ServicePersistentException {
        UserDao userDao = transaction.createUserDao();
        try {
            return userDao.read(login);
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Finds user by id
     *
     * @param id
     * @return found user
     * @throws ServicePersistentException
     */
    @Override
    public User findById(Integer id) throws ServicePersistentException {
        UserDao userDao = transaction.createUserDao();
        try {
            User user = userDao.read(id);
            if (user != null) {
                return user;
            } else {
                throw new ServicePersistentException("User wasn't found");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Finds all users
     *
     * @return list of found users
     * @throws ServicePersistentException
     */
    @Override
    public List<User> findAll() throws ServicePersistentException {
        UserDao userDao = transaction.createUserDao();
        try {
            List<User> users = userDao.read();
            if (!users.isEmpty()) {
                return users;
            } else {
                throw new ServicePersistentException("Users were not found");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Finds all users with specified offset and number of records
     *
     * @param offset
     * @param noOfRecords
     * @return
     * @throws ServicePersistentException
     */
    @Override
    public Map<Integer, List<User>> find(int offset, int noOfRecords) throws ServicePersistentException {
        try {
            UserDao userDao = transaction.createUserDao();
            Map<Integer, List<User>> map = userDao.read(offset, noOfRecords);
            return map;
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }
}
