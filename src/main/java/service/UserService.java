package service;

import domain.User;
import exception.PersistentException;

import java.util.List;

public interface UserService extends Service {
    void save(User user) throws PersistentException;

    void delete(Integer id) throws PersistentException;

    User findByLoginAndPassword(String login, String password) throws PersistentException;

    User findById(Integer id) throws PersistentException;

    List<User> findAll() throws PersistentException;
}
