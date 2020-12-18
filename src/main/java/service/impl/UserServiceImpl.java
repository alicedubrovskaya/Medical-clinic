package service.impl;

import dao.UserDao;
import domain.User;
import exception.PersistentException;
import service.UserService;

public class UserServiceImpl extends ServiceImpl implements UserService {
    @Override
    public User findByLoginAndPassword(String login, String password) throws PersistentException {
        return null;
    }

    @Override
    public void save(User user) throws PersistentException {

    }

    @Override
    public void delete(Integer id) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        userDao.delete(id);
    }
}
