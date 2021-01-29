package controller.action;

import domain.User;
import domain.enumeration.Role;
import exception.PersistentException;
import service.PatientService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class UserEditCommand extends Command {

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        try {
            String parameter = request.getParameter("role");
            if (parameter != null) {
                request.setAttribute("role", Role.getEnum(parameter).getId());
            } else {
                HttpSession session = request.getSession(false);
                User authorizedUser = (User) session.getAttribute("authorizedUser");
                Integer userId = null;
                if (authorizedUser.getRole() == Role.ADMINISTRATOR) {
                    userId = Integer.parseInt(request.getParameter("id"));
                } else {
                    userId = authorizedUser.getId();
                }
                if (userId != null) {
                    UserService service = serviceFactory.getUserService();
                    User user = service.findById(userId);
                    if (user != null) {
                        request.setAttribute("user", user);
                    }
                }
            }
        } catch (NumberFormatException e) {
        }
        return null;
    }
}
