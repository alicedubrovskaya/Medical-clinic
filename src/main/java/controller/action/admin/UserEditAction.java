package controller.action.admin;

import domain.Doctor;
import domain.enumeration.Role;
import exception.PersistentException;
import service.DoctorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserEditAction extends AdministratorAction {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        request.setAttribute("roles", Role.values());
        try {
//            Integer id = (Integer) request.getAttribute("id");
//            if (id == null) {
//                id = Integer.parseInt(request.getParameter("id"));
//            }
//            DoctorService service = serviceFactory.getDoctorService();
//            Doctor doctor = service.findById(id);
//            if (doctor != null) {
//                request.setAttribute("doctor", doctor);
//            }
        } catch (NumberFormatException e) {
        }
        return null;
    }
}
