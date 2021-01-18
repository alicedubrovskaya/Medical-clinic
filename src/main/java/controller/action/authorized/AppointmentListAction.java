package controller.action.authorized;

import controller.action.Action;
import domain.Appointment;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import service.AppointmentService;
import validator.DateValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class AppointmentListAction extends AuthorizedUserAction {
    @Override
    public Action.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        DateValidator validator = validatorFactory.createDateValidator();
        Date date = null;
        String specialization = null;
        try {
            date = validator.validateDate(request);
            specialization = request.getParameter("specialization");
        } catch (IncorrectFormDataException e) {
            e.printStackTrace();
        }

        AppointmentService service = serviceFactory.getAppointmentService();
        List<Appointment> appointments;
        if (date != null && specialization != null) {
            appointments = service.findByTimeAndSpecialization(date, specialization);
        } else {
            appointments = service.findAll();
        }
        request.setAttribute("appointments", appointments);
        return null;
    }
}
