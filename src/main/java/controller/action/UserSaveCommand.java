package controller.action;

import domain.User;
import domain.enumeration.Role;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import service.UserService;
import validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class UserSaveCommand extends Command {

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }

    @Override
    public Command.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Command.Forward forward = null;
        try {
            Validator<User> validator = validatorFactory.createUserValidator();
            User user = validator.validate(request);
            UserService service = serviceFactory.getUserService();

            User existingUser = service.findByLogin(user.getLogin());
            if (existingUser == null) {
//                service.save(user);
                if (user.getRole() == Role.PATIENT) {
                    forward = new Command.Forward("/patient/edit.html");
                } else {
                    forward = new Command.Forward("/doctor/edit.html");
                }
                forward.getAttributes().put("user", user);
//                forward.getAttributes().put("message", "Данные пользователя успешно сохранены");
            } else {
                forward = new Command.Forward("/user/edit.html");
                forward.getAttributes().put("message", "Пользователь с данным логином уже существует");
            }

        } catch (IncorrectFormDataException e) {
            e.printStackTrace();
        }
        return forward;
    }
}
