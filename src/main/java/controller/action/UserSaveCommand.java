package controller.action;

import domain.User;
import domain.enumeration.Role;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import service.exception.ServicePersistentException;
import service.impl.UserServiceImpl;
import validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class UserSaveCommand extends Command {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }

    @Override
    public Command.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Command.Forward forward = new Forward("/main.html");
        try {
            Validator<User> validator = validatorFactory.createUserValidator();
            User user = validator.validate(request);
            UserService service = serviceFactory.getUserService();

            HttpSession session = request.getSession(false);
            User authorizedUser = (User) session.getAttribute("authorizedUser");

            String parameter = request.getParameter("registration");
            if (parameter != null) {
                try {
                    User existingUser = service.findByLogin(user.getLogin());
                    if (existingUser == null) {
                        if (authorizedUser == null) {
                            forward = new Command.Forward("/patient/edit.html");
                        } else {
                            forward = new Command.Forward("/doctor/edit.html");
                        }
                        forward.getAttributes().put("user", user);
                    } else {
                        forward = new Command.Forward("/user/edit.html");
                        forward.getAttributes().put("registration", true);
                        forward.getAttributes().put("message", "Пользователь с данным логином уже существует");
                    }
                } catch (ServicePersistentException e) {
                    logger.error(e);
                }
            } else {
                try {
                    String oldPassword = request.getParameter("old_password");
                    service.findByLoginAndPassword(user.getLogin(), oldPassword);
                    service.save(user);
                    forward = new Command.Forward("/user/edit.html");
                    forward.getAttributes().put("user", user);
                    forward.getAttributes().put("message", "Пароль успешно изменен");
                } catch (ServicePersistentException e) {
                    logger.error(e);
                }
            }
        } catch (
                IncorrectFormDataException e) {
            e.printStackTrace();
        }
        return forward;
    }
}
