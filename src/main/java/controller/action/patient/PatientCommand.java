package controller.action.patient;

import controller.action.Command;
import domain.enumeration.Role;

/**
 * Patient command abstract class
 */
public abstract class PatientCommand extends Command {
    /**
     * Only users with Role.PATIENT can have access to commands that extend this class
     */
    protected PatientCommand() {
        getAllowRoles().add(Role.PATIENT);
    }
}
