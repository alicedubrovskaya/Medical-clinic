package by.dubrovskaya.command.authorized;

import by.dubrovskaya.command.Command;
import by.dubrovskaya.domain.enumeration.Role;

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
