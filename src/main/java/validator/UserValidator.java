package validator;

import domain.User;
import domain.enumeration.Role;
import domain.enumeration.Shift;
import exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class UserValidator implements Validator<User> {
    @Override
    public User validate(HttpServletRequest request) throws IncorrectFormDataException {
        User user = new User();
        String parameter = request.getParameter("id");
        if (parameter != null) {
            try {
                user.setId(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("id", parameter);
            }
        }

        parameter = request.getParameter("login");
        if (parameter != null) {
            try {
                user.setLogin(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("login", parameter);
            }
        }

        parameter = request.getParameter("password");
        if (parameter != null) {
            try {
                user.setPassword(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("password", parameter);
            }
        }

        parameter = request.getParameter("role");
        if (parameter != null) {
            try {
                user.setRole(Role.getById(Integer.parseInt(parameter)));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("role", parameter);
            }
        }

        return user;
    }
}
