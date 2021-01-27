package controller.action;

import domain.enumeration.Role;
import exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            }
        } catch (NumberFormatException e) {
        }
        return null;
    }
}
