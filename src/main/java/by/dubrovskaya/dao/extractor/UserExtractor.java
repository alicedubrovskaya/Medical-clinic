package by.dubrovskaya.dao.extractor;

import by.dubrovskaya.domain.User;
import by.dubrovskaya.domain.enumeration.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserExtractor implements Extractor<User> {
    @Override
    public User extract(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(Role.getById(resultSet.getInt("role")));
        return user;
    }
}
