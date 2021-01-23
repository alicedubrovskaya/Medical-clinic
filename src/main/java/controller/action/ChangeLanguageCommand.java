package controller.action;

import domain.enumeration.Role;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;


public class ChangeLanguageCommand extends Command {
    private static final Logger logger = LogManager.getLogger(ChangeLanguageCommand.class);

    private static final String LANGUAGE = "language";

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        //TODO forward
        Forward forward =  forward = new Forward("/doctor/list.html");
        String language = request.getParameter("language");
        if (language != null) {
            request.getSession().setAttribute("language", language);
            response.addCookie(new Cookie("language", language));
        }

        return forward;
    }
}
