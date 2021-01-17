package controller.action.admin.user;

import controller.action.admin.AdministratorAction;
import domain.User;
import domain.enumeration.Role;
import exception.PersistentException;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserEditAction extends AdministratorAction {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        request.setAttribute("roles", Role.values());
        try {
            Integer id = (Integer) request.getAttribute("id");
            if (id == null) {
                id = Integer.parseInt(request.getParameter("id"));
            }
            UserService service = serviceFactory.getUserService();
            User user = service.findById(id);
            if (user != null) {
                request.setAttribute("user", user);
            }
        } catch (NumberFormatException e) {
        }
        return null;
    }
}
