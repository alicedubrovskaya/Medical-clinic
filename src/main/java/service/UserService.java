package service;

import domain.User;
import exception.PersistentException;

public interface UserService extends Service {
    User findByLoginAndPassword(String login, String password) throws PersistentException;

    void save(User user) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
