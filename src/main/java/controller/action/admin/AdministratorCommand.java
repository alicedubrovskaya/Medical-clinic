package controller.action.admin;

import controller.action.Command;
import domain.enumeration.Role;

abstract public class AdministratorCommand extends Command {
	public AdministratorCommand() {
		getAllowRoles().add(Role.ADMINISTRATOR);
	}
}
