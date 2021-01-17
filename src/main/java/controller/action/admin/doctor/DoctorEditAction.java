package controller.action.admin.doctor;

import controller.action.admin.AdministratorAction;
import domain.Doctor;
import exception.PersistentException;
import service.DoctorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoctorEditAction extends AdministratorAction {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        try {
            Integer id = (Integer) request.getAttribute("id");
            if (id == null) {
                id = Integer.parseInt(request.getParameter("id"));
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
