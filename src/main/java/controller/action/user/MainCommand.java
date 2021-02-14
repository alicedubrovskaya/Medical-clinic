package controller.action.user;

import controller.action.Command;
import domain.enumeration.Role;
import exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class MainCommand extends Command {
    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
//		List<MenuItem> menu = (List<MenuItem>)request.getSession(false).getAttribute("menu");
//		return new Forward(menu.get(0).getUrl());
        return null;
    }
}
