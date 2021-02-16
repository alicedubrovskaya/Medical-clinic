package by.dubrovskaya.command.admin;

import by.dubrovskaya.command.Command;
import by.dubrovskaya.domain.enumeration.Role;

/**
 * Administrator command abstract class
 */
public abstract class AdministratorCommand extends Command {

    /**
     * Only users with Role.ADMINISTRATOR can have access to commands that extend this class
     */
    protected AdministratorCommand() {
        getAllowRoles().add(Role.ADMINISTRATOR);
    }
}