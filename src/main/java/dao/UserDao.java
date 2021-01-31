package dao;

import domain.User;
import exception.PersistentException;

import java.util.List;
import java.util.Map;

public interface UserDao extends Dao<User> {
    User read(String login, String password) throws PersistentException;

    User read(String login) throws PersistentException;

    List<User> read() throws PersistentException;

    Map<Integer, List<User>> read(int offset, int noOfRecords) throws PersistentException;

}
