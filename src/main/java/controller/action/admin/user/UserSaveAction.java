package controller.action.admin.user;

import controller.action.Action;
import controller.action.admin.AdministratorAction;
import domain.User;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import service.UserService;
import validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserSaveAction extends AdministratorAction {

    @Override
    public Action.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Action.Forward forward = new Action.Forward("/user/edit.html");
        try {
            Validator<User> validator = validatorFactory.createUserValidator();
            User user = validator.validate(request);
            UserService service = serviceFactory.getUserService();
            service.save(user);
            forward.getAttributes().put("id", user.getId());
            forward.getAttributes().put("message", "Данные пользователя успешно сохранены");

        } catch (IncorrectFormDataException e) {
            e.printStackTrace();
        }
        return forward;
    }
}
