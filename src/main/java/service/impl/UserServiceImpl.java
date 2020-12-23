package service.impl;

import dao.UserDao;
import domain.User;
import exception.PersistentException;
import service.PasswordEncryption;
import service.UserService;

public class UserServiceImpl extends ServiceImpl implements UserService {

    @Override
    public void save(User user) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
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
        UserDao userDao = transaction.createDao(UserDao.class);
        userDao.delete(id);
    }

    @Override
    public User findByLoginAndPassword(String login, String password) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        return userDao.read(login, PasswordEncryption.encrypt(password));
    }

    @Override
    public User findById(Integer id) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        return userDao.read(id);
    }

}
