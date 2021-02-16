package by.dubrovskaya.controller.validator;

import by.dubrovskaya.domain.enumeration.ParameterType;
import by.dubrovskaya.domain.User;
import by.dubrovskaya.domain.enumeration.Role;
import by.dubrovskaya.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class UserValidator implements Validator<User> {
    @Override
    public User validate(HttpServletRequest request) throws IncorrectFormDataException {
        User user = new User();
        String parameter = request.getParameter(ParameterType.ID.getValue());
        if (parameter != null) {
            try {
                user.setId(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.ID.getValue(), parameter);
            }
        }

        parameter = request.getParameter(ParameterType.LOGIN.getValue());
        if (parameter != null) {
            try {
                user.setLogin(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.LOGIN.getValue(), parameter);
            }
        }

        parameter = request.getParameter(ParameterType.PASSWORD.getValue());
        if (parameter != null) {
            try {
                user.setPassword(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.PASSWORD.getValue(), parameter);
            }
        }

        parameter = request.getParameter(ParameterType.ROLE.getValue());
        if (parameter != null) {
            try {
                user.setRole(Role.getById(Integer.parseInt(parameter)));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.ROLE.getValue(), parameter);
            }
        }

        return user;
    }
}
