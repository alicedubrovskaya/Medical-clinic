package controller.action.authorized;

import controller.action.MenuItem;
import exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MainCommand extends AuthorizedUserCommand {
	@Override
	public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
		List<MenuItem> menu = (List<MenuItem>)request.getSession(false).getAttribute("menu");
		return new Forward(menu.get(0).getUrl());
	}
}
