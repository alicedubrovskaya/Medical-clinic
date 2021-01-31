package service.impl;

import dao.UserDao;
import domain.User;
import exception.PersistentException;
import service.PasswordEncryption;
import service.UserService;
import service.exception.ServicePersistentException;

import java.util.List;
import java.util.Map;

public class UserServiceImpl extends ServiceImpl implements UserService {

    @Override
    public void save(User user) throws PersistentException {
        UserDao userDao = transaction.createUserDao();
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
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        UserDao userDao = transaction.createUserDao();
        userDao.delete(id);
    }

    @Override
    public User findByLoginAndPassword(String login, String password) throws PersistentException {
        UserDao userDao = transaction.createUserDao();
        return userDao.read(login, PasswordEncryption.encrypt(password));
    }

    @Override
    public User findByLogin(String login) throws PersistentException {
        UserDao userDao = transaction.createUserDao();
        return userDao.read(login);
    }

    @Override
    public User findById(Integer id) throws PersistentException {
        UserDao userDao = transaction.createUserDao();
        return userDao.read(id);
    }

    @Override
    public List<User> findAll() throws PersistentException {
        UserDao userDao = transaction.createUserDao();
        return userDao.read();
    }

    /**
     * Reads all users with specified offset and number of records
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
