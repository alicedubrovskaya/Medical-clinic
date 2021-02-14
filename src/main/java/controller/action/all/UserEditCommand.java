package controller.action.all;

import controller.action.Command;
import controller.enumeration.AttributeType;
import controller.enumeration.ParameterType;
import domain.User;
import domain.enumeration.Role;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class UserEditCommand extends Command {
    private static final Logger logger = LogManager.getLogger(UserEditCommand.class);

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        try {
            String parameter = request.getParameter(ParameterType.REGISTRATION.getValue());
            if (parameter != null) {
                request.setAttribute(AttributeType.REGISTRATION.getValue(), parameter);
            }
            HttpSession session = request.getSession(false);
            User authorizedUser = (User) session.getAttribute(AttributeType.USER_AUTHORIZED.getValue());
            Integer userId;
            if (authorizedUser != null) {
                if (authorizedUser.getRole() == Role.ADMINISTRATOR) {
                    userId = Integer.parseInt(request.getParameter(ParameterType.ID.getValue()));
                } else {
                    userId = authorizedUser.getId();
                }
                if (userId != null) {
                    UserService service = serviceFactory.getUserService();
                    User user = service.findById(userId);
                    request.setAttribute(AttributeType.USER.getValue(), user);
                }
            }
        } catch (NumberFormatException | ServicePersistentException e) {
            logger.error(e);
        }
        return null;
    }
}
