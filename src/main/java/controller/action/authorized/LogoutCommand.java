package controller.action.authorized;

import controller.enumeration.CommandType;
import exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand extends AuthorizedUserCommand {
    private static final String HTML = ".html";

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        request.getSession(false).invalidate();
        return new Forward(CommandType.MAIN.getCommand()+HTML);
    }
}
