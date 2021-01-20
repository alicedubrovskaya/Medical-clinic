package controller.action.authorized;

import domain.Doctor;
import domain.User;
import exception.PersistentException;
import service.DoctorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class DoctorEditAction extends AuthorizedUserAction {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        DoctorService service = serviceFactory.getDoctorService();
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
                Doctor doctor = service.findById(id);
                request.setAttribute("doctor", doctor);
            } else {
                request.setAttribute("user", user);
            }
            request.setAttribute("specializations", service.findAllSpecializations());
            request.setAttribute("workingShifts", Arrays.asList("Первая", "Вторая"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
