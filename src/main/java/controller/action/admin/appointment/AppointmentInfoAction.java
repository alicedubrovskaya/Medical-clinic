package controller.action.admin.appointment;

import controller.action.admin.AdministratorAction;
import domain.Appointment;
import domain.Doctor;
import exception.PersistentException;
import service.AppointmentService;
import service.DoctorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppointmentInfoAction extends AdministratorAction {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        try {
            Integer id = (Integer) request.getAttribute("id");
            if (id == null) {
                id = Integer.parseInt(request.getParameter("id"));
            }
            AppointmentService service = serviceFactory.getAppointmentService();
            Appointment appointment = service.findById(id);
            if (appointment != null) {
                request.setAttribute("appointment", appointment);
            }
        } catch (NumberFormatException e) {
        }
        return null;
    }
}
