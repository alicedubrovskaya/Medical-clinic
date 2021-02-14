package controller.action.user;

import controller.action.Command;
import controller.enumeration.AttributeType;
import controller.enumeration.CommandType;
import controller.enumeration.ParameterType;
import domain.enumeration.Role;
import exception.PersistentException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;


public class ChangeLanguageCommand extends Command {
    private static final String HTML = ".html";

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward(CommandType.MAIN.getCommand() + HTML);
        String language = request.getParameter(ParameterType.LANGUAGE.getValue());
        if (language != null) {
            request.getSession().setAttribute(AttributeType.LANGUAGE.getValue(), language);
            response.addCookie(new Cookie(AttributeType.LANGUAGE.getValue(), language));
        }
        return forward;
    }
}
