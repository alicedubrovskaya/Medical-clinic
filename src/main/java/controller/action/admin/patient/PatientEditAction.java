package controller.action.admin.patient;

import controller.action.admin.AdministratorAction;
import domain.Patient;
import exception.PersistentException;
import service.PatientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PatientEditAction extends AdministratorAction {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        try {
            Integer id = (Integer) request.getAttribute("id");
            if (id == null) {
                id = Integer.parseInt(request.getParameter("id"));
            }
            PatientService service = serviceFactory.getPatientService();
            Patient patient = service.findById(id);
            if (patient != null) {
                request.setAttribute("patient", patient);
            }
        } catch (NumberFormatException e) {
        }
        return null;
    }
}
