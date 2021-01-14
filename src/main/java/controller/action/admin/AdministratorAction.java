package controller.action.admin;

import controller.action.Action;
import domain.enumeration.Role;

abstract public class AdministratorAction extends Action {
	public AdministratorAction() {
		getAllowRoles().add(Role.ADMINISTRATOR);
	}
}
