package service;

import domain.User;
import exception.PersistentException;
import service.exception.ServicePersistentException;

import java.util.List;
import java.util.Map;

public interface UserService extends Service {
    void save(User user) throws PersistentException;

    void delete(Integer id) throws PersistentException;

    User findByLoginAndPassword(String login, String password) throws PersistentException;

    User findByLogin(String login) throws PersistentException;

    User findById(Integer id) throws PersistentException;

    List<User> findAll() throws PersistentException;

    Map<Integer, List<User>> find(int offset, int noOfRecords) throws ServicePersistentException;

}
