package controller.action.authorized;

import controller.action.Command;
import domain.enumeration.Role;

import java.util.Arrays;

public abstract class AuthorizedUserCommand extends Command {
	public AuthorizedUserCommand() {
		getAllowRoles().addAll(Arrays.asList(Role.values()));
	}
}
