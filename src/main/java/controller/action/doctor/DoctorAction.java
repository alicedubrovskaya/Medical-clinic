package controller.action.doctor;

import controller.action.Action;
import domain.enumeration.Role;

abstract public class DoctorAction extends Action {
    public DoctorAction() {
        getAllowRoles().add(Role.DOCTOR);
    }
}
