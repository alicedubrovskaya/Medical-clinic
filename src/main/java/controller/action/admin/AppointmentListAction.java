package controller.action.admin;

import domain.Appointment;
import exception.PersistentException;
import service.AppointmentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AppointmentListAction extends AdministratorAction {
    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        AppointmentService service = serviceFactory.getAppointmentService();
        List<Appointment> appointments = service.findAll();
        request.setAttribute("appointments", appointments);
        return null;
    }
}
