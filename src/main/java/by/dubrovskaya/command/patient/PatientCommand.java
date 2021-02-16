package by.dubrovskaya.command.patient;

import by.dubrovskaya.command.Command;
import by.dubrovskaya.domain.enumeration.Role;

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
