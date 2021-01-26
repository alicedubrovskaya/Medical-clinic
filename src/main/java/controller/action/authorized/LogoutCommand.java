package controller.action.authorized;

import exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand extends AuthorizedUserCommand {
    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        request.getSession(false).invalidate();
        return new Forward("/main.html");
    }
}
