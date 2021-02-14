package controller.action.doctor;

import controller.action.Command;
import domain.enumeration.Role;

/**
 * Doctor command abstract class
 */
public abstract class DoctorCommand extends Command {
    /**
     * Only users with Role.DOCTOR can have access to commands that extend this class
     */
    protected DoctorCommand() {
        getAllowRoles().add(Role.DOCTOR);
    }
}
