package controller.action.admin;

import controller.action.Command;
import domain.enumeration.Role;

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