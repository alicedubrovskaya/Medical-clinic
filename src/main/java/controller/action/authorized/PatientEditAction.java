package controller.action.authorized;

import domain.Patient;
import exception.PersistentException;
import service.PatientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PatientEditAction extends AuthorizedUserAction {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        try {
            Integer id = (Integer) request.getAttribute("id");
            if (id == null) {
                String parameter = request.getParameter("id");
                if (parameter != null) {
                    id = Integer.parseInt(parameter);
                }
            }
            if (id == null) {
                id = getAuthorizedUser().getId();
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
