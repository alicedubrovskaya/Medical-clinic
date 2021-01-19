package controller.action.authorized;

import domain.Doctor;
import exception.PersistentException;
import service.DoctorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoctorEditAction extends AuthorizedUserAction {

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
            DoctorService service = serviceFactory.getDoctorService();
            Doctor doctor = service.findById(id);
            if (doctor != null) {
                request.setAttribute("doctor", doctor);
            }
        } catch (NumberFormatException e) {
        }
        return null;
    }
}
