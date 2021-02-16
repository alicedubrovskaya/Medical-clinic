package by.dubrovskaya.command.user;

import by.dubrovskaya.command.Command;
import by.dubrovskaya.domain.enumeration.Role;
import by.dubrovskaya.exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class MainCommand extends Command {
    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
//		List<MenuItem> menu = (List<MenuItem>)request.getSession(false).getAttribute("menu");
//		return new Forward(menu.get(0).getUrl());
        return null;
    }
}
