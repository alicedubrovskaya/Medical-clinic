package controller.action;

import domain.User;
import domain.enumeration.Role;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.PatientService;
import service.Service;
import service.UserService;
import service.exception.ServicePersistentException;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class UserEditCommand extends Command {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        try {
            String parameter = request.getParameter("registration");
            if (parameter != null) {
                request.setAttribute("registration", parameter);
            }
            HttpSession session = request.getSession(false);
            User authorizedUser = (User) session.getAttribute("authorizedUser");
            Integer userId = null;
            if (authorizedUser != null) {
                if (authorizedUser.getRole() == Role.ADMINISTRATOR) {
                    userId = Integer.parseInt(request.getParameter("id"));
                } else {
                    userId = authorizedUser.getId();
                }
                if (userId != null) {
                    UserService service = serviceFactory.getUserService();
                    try {
                        User user = service.findById(userId);
                        request.setAttribute("user", user);
                    } catch (ServicePersistentException e) {
                        logger.error(e);
                    }
                }
            }
//            String parameter = request.getParameter("role");
//            if (parameter != null) {
//                request.setAttribute("role", Role.getEnum(parameter).getId());
//            } else {
//                HttpSession session = request.getSession(false);
//                User authorizedUser = (User) session.getAttribute("authorizedUser");
//                Integer userId = null;
//                if (authorizedUser.getRole() == Role.ADMINISTRATOR) {
//                    userId = Integer.parseInt(request.getParameter("id"));
//                } else {
//                    userId = authorizedUser.getId();
//                }
//                if (userId != null) {
//                    UserService service = serviceFactory.getUserService();
//                    try {
//                        User user = service.findById(userId);
//                        request.setAttribute("user", user);
//                    } catch (ServicePersistentException e) {
//                        logger.error(e);
//                    }
//                }
//            }
        } catch (NumberFormatException e) {
        }
        return null;
    }
}
