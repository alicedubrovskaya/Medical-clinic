package controller.action.patient;

import controller.action.Command;
import domain.enumeration.Role;

abstract public class PatientCommand extends Command {
    public PatientCommand() {
        getAllowRoles().add(Role.PATIENT);
    }
}
