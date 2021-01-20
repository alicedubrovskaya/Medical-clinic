package dao;

import domain.Doctor;
import domain.User;
import exception.PersistentException;

import java.util.List;

public interface UserDao extends Dao<User> {
    User read(String login, String password) throws PersistentException;

    User read(String login) throws PersistentException;

    List<User> read() throws PersistentException;
}
