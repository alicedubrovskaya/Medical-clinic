package controller.action.authorized;

import domain.Doctor;
import domain.User;
import exception.PersistentException;
import service.DoctorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class DoctorEditCommand extends AuthorizedUserCommand {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        DoctorService service = serviceFactory.getDoctorService();
        HttpSession session = request.getSession(false);
        User authorizedUser = (User) session.getAttribute("authorizedUser");
        try {
            User user = (User) request.getAttribute("user");
            if (user == null) {
                Integer id;
                String parameter = request.getParameter("id");
                if (parameter != null) {
                    id = Integer.parseInt(parameter);
                } else {
                    id = authorizedUser.getId();
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
