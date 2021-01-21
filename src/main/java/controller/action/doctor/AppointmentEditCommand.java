package controller.action.doctor;

import domain.Appointment;
import exception.PersistentException;
import service.AppointmentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class AppointmentEditCommand extends DoctorCommand {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        try {
            Integer id = (Integer) request.getAttribute("appointmentId");
            if (id == null) {
                id = Integer.parseInt(request.getParameter("appointmentId"));
            }
            AppointmentService service = serviceFactory.getAppointmentService();
            Appointment appointment = service.findById(id);
            if (appointment != null) {
                request.setAttribute("appointment", appointment);
                request.setAttribute("statuses", Arrays.asList("Был", "Не был"));
            }
        } catch (NumberFormatException e) {
        }
        return null;
    }
}
