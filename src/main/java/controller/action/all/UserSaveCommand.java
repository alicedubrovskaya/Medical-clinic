package controller.action.all;

import controller.action.Command;
import controller.enumeration.AttributeType;
import controller.enumeration.CommandType;
import controller.enumeration.ParameterType;
import domain.User;
import domain.enumeration.Role;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import service.exception.ServicePersistentException;
import validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class UserSaveCommand extends Command {
    private static final Logger logger = LogManager.getLogger(UserSaveCommand.class);
    private static final String HTML = ".html";

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }

    @Override
    public Command.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Command.Forward forward = new Forward(CommandType.MAIN.getCommand() + HTML);
        try {
            Validator<User> validator = validatorFactory.createUserValidator();
            User user = validator.validate(request);
            UserService service = serviceFactory.getUserService();

            HttpSession session = request.getSession(false);
            User authorizedUser = (User) session.getAttribute(AttributeType.USER_AUTHORIZED.getValue());

            String parameter = request.getParameter(ParameterType.REGISTRATION.getValue());
            if (parameter != null) {
                User existingUser = service.findByLogin(user.getLogin());
                if (existingUser == null) {
                    if (authorizedUser == null) {
                        forward = new Command.Forward(CommandType.PATIENT_EDIT.getCommand() + HTML);
                    } else {
                        forward = new Command.Forward(CommandType.DOCTOR_EDIT.getCommand() + HTML);
                    }
                    forward.getAttributes().put(AttributeType.USER.getValue(), user);
                } else {
                    forward = new Command.Forward(CommandType.USER_EDIT.getCommand() + HTML);
                    forward.getAttributes().put(AttributeType.REGISTRATION.getValue(), true);
                    forward.getAttributes().put("message", "Пользователь с данным логином уже существует");
                }
            } else {
                String oldPassword = request.getParameter(ParameterType.PASSWORD_OLD.getValue());
                service.findByLoginAndPassword(user.getLogin(), oldPassword);
                service.save(user);
                forward = new Command.Forward(CommandType.USER_EDIT.getCommand() + HTML);
                forward.getAttributes().put(AttributeType.USER.getValue(), user);
                forward.getAttributes().put("message", "Пароль успешно изменен");
            }
        } catch (IncorrectFormDataException | ServicePersistentException e) {
            logger.error(e);
        }
        return forward;
    }
}
