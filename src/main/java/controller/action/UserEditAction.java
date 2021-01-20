package controller.action;

import domain.User;
import domain.enumeration.Role;
import exception.PersistentException;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class UserEditAction extends Action {

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        try {

            if (getAuthorizedUser() == null) {
                request.setAttribute("role", Role.PATIENT.getId());
            }

//            Integer id = (Integer) request.getAttribute("id");
//            if (id == null) {
//                id = Integer.parseInt(request.getParameter("id"));
//            }
//            UserService service = serviceFactory.getUserService();
//            User user = service.findById(id);
//            if (user != null) {
//                request.setAttribute("user", user);
//            }
        } catch (NumberFormatException e) {
        }
        return null;
    }
}
