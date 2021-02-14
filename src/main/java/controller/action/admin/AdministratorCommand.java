package controller.action.admin;

import controller.action.Command;
import domain.enumeration.Role;

/**
 * Administrator command abstract class
 */
abstract public class AdministratorCommand extends Command {

    /**
     * Only users with Role.ADMINISTRATOR can have access to commands that extend this class
     */
    public AdministratorCommand() {
        getAllowRoles().add(Role.ADMINISTRATOR);
    }
}
