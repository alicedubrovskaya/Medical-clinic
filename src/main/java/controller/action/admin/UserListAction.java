package controller.action.admin;

import domain.User;
import exception.PersistentException;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserListAction extends AdministratorAction {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        UserService service = serviceFactory.getUserService();
        List<User> users = service.findAll();
        request.setAttribute("users", users);
        return null;
    }
}
