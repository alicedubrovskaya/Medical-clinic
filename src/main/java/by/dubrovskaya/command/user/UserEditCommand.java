package by.dubrovskaya.command.user;

import by.dubrovskaya.command.Command;
import by.dubrovskaya.domain.User;
import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.enumeration.ParameterType;
import by.dubrovskaya.domain.enumeration.Role;
import by.dubrovskaya.exception.ServicePersistentException;
import by.dubrovskaya.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public Forward exec(HttpServletRequest request, HttpServletResponse response){
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
