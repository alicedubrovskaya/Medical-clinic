package controller.action.authorized;

import domain.Appointment;
import domain.User;
import domain.enumeration.Role;
import exception.PersistentException;
import service.AppointmentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class MedicalCardCommand extends AuthorizedUserCommand {
    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        AppointmentService service = serviceFactory.getAppointmentService();
        String parameter = request.getParameter("id");
        Integer id = null;
        if (parameter == null) {
            HttpSession session = request.getSession(false);
            User authorizedUser = (User) session.getAttribute("authorizedUser");
            if (authorizedUser.getRole().equals(Role.PATIENT)) {
                id = authorizedUser.getId();
            }
        } else {
            id = Integer.parseInt(parameter);
        }

        if (id != null) {
            List<Appointment> appointments = service.findByPatient(id);
            request.setAttribute("appointments", appointments);
        }
        return null;
    }
}
