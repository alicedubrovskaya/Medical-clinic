package controller.action.admin.patient;

import controller.action.admin.AdministratorAction;
import domain.Doctor;
import domain.Patient;
import exception.PersistentException;
import service.DoctorService;
import service.PatientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PatientListAction extends AdministratorAction {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        PatientService service = serviceFactory.getPatientService();
        List<Patient> patients = service.findAll();
        request.setAttribute("patients", patients);
        return null;
    }
}
