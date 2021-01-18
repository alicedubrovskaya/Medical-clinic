package controller.action.patient;

import controller.action.Action;
import domain.enumeration.Role;

abstract public class PatientAction extends Action {
    public PatientAction() {
        getAllowRoles().add(Role.PATIENT);
    }
}
