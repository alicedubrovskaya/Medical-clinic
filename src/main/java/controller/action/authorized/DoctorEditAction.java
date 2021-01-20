package controller.action.authorized;

import domain.Doctor;
import domain.User;
import exception.PersistentException;
import service.DoctorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoctorEditAction extends AuthorizedUserAction {

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
                DoctorService service = serviceFactory.getDoctorService();
                Doctor doctor = service.findById(id);
                request.setAttribute("doctor", doctor);
            } else {
                request.setAttribute("user", user);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
