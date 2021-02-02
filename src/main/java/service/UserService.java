package service;

import domain.User;
import exception.PersistentException;
import service.exception.ServicePersistentException;

import java.util.List;
import java.util.Map;

public interface UserService extends Service {
    void save(User user) throws ServicePersistentException;

    void delete(Integer id) throws ServicePersistentException;

    User findByLoginAndPassword(String login, String password) throws ServicePersistentException;

    User findByLogin(String login) throws ServicePersistentException;

    User findById(Integer id) throws ServicePersistentException;

    List<User> findAll() throws ServicePersistentException;

    Map<Integer, List<User>> find(int offset, int noOfRecords) throws ServicePersistentException;

}
