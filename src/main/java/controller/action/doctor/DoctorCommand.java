package controller.action.doctor;

import controller.action.Command;
import domain.enumeration.Role;

abstract public class DoctorCommand extends Command {
    public DoctorCommand() {
        getAllowRoles().add(Role.DOCTOR);
    }
}
