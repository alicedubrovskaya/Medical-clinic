package controller.action.authorized;

import controller.action.Command;
import domain.enumeration.Role;

import java.util.Arrays;

/**
 * Authorized user command abstract class
 */
public abstract class AuthorizedUserCommand extends Command {
	/**
	 * Only authorized users can have access to commands that extend this class
	 */
	protected AuthorizedUserCommand() {
		getAllowRoles().addAll(Arrays.asList(Role.values()));
	}
}
