package dao;

import domain.User;
import exception.PersistentException;

public interface UserDao extends Dao<User> {
    User read(String login, String password) throws PersistentException;
}
