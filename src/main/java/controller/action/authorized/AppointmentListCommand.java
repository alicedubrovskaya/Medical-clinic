package controller.action.authorized;

import controller.action.Command;
import domain.Appointment;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import service.AppointmentService;
import validator.DateValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class AppointmentListCommand extends AuthorizedUserCommand {
    @Override
    public Command.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {

        DateValidator validator = validatorFactory.createDateValidator();
        Date date = null;
        String specialization = null;
        String status = null;
        Integer doctorId = null;
        try {
            date = validator.validateDate(request);
            specialization = request.getParameter("specialization");
            status = request.getParameter("status");
            if (request.getParameter("doctorId") != null) {
                doctorId = Integer.valueOf(request.getParameter("doctorId"));
            } else {
                doctorId = getAuthorizedUser().getId();
            }

        } catch (IncorrectFormDataException e) {
            e.printStackTrace();
        }

        AppointmentService service = serviceFactory.getAppointmentService();
        List<Appointment> appointments;
        if (date != null && specialization != null) {
            appointments = service.findByTimeAndSpecialization(date, specialization);
        } else if (date != null && status != null && doctorId != 0) {
            appointments = service.findByDateAndStatusAndDoctor(date, status, doctorId);
        } else if (date != null) {
            appointments = service.findByTime(date);
        } else {
            appointments = service.findAll();
        }
        request.setAttribute("appointments", appointments);
        return null;
    }
}
