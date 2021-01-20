package controller.action;

import domain.Patient;
import domain.User;
import domain.enumeration.Role;
import exception.PersistentException;
import service.PatientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class PatientEditAction extends Action {

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }


    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        try {
            User user = (User) request.getAttribute("user");
            if (user == null) {
                Integer id;
                String parameter = request.getParameter("id");
                if (parameter != null) {
                    id = Integer.parseInt(parameter);
                } else {
                    id = getAuthorizedUser().getId();
                }
                PatientService service = serviceFactory.getPatientService();
                Patient patient = service.findById(id);
                request.setAttribute("patient", patient);
            } else {
                request.setAttribute("user", user);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
