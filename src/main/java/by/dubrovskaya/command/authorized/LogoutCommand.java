package by.dubrovskaya.command.authorized;

import by.dubrovskaya.domain.enumeration.CommandType;
import by.dubrovskaya.exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand extends AuthorizedUserCommand {
    private static final String HTML = ".html";

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response){
        request.getSession(false).invalidate();
        return new Forward(CommandType.MAIN.getCommand()+HTML);
    }
}
