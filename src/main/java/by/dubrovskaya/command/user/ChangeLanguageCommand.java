package by.dubrovskaya.command.user;

import by.dubrovskaya.command.Command;
import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.enumeration.CommandType;
import by.dubrovskaya.domain.enumeration.ParameterType;
import by.dubrovskaya.domain.enumeration.Role;

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
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        Forward forward = new Forward(CommandType.MAIN.getCommand() + HTML);
        String language = request.getParameter(ParameterType.LANGUAGE.getValue());
        if (language != null) {
            request.getSession().setAttribute(AttributeType.LANGUAGE.getValue(), language);
        }
        return forward;
    }
}
