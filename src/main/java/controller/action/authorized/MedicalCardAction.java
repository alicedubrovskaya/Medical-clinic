package controller.action.authorized;

import controller.action.authorized.AuthorizedUserAction;
import domain.Appointment;
import domain.enumeration.Role;
import exception.PersistentException;
import service.AppointmentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MedicalCardAction extends AuthorizedUserAction {
    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        AppointmentService service = serviceFactory.getAppointmentService();
        String parameter = request.getParameter("id");
        Integer id = null;
        if (parameter == null) {
            if (getAuthorizedUser().getRole().equals(Role.PATIENT)) {
                id = getAuthorizedUser().getId();
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
