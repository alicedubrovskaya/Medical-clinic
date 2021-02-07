package controller.action.authorized;

import controller.action.Command;
import domain.Appointment;
import domain.User;
import domain.enumeration.Role;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import service.AppointmentService;
import validator.DateValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

public class AppointmentListCommand extends AuthorizedUserCommand {
    @Override
    public Command.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        HttpSession session = request.getSession(false);
        User authorizedUser = (User) session.getAttribute("authorizedUser");

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
                doctorId = authorizedUser.getId();
            }

        } catch (IncorrectFormDataException e) {
            e.printStackTrace();
        }

        AppointmentService service = serviceFactory.getAppointmentService();
        List<Appointment> appointments;

        if (date != null) {
            if (specialization != null) {
                appointments = service.findByTimeAndSpecialization(date, specialization);
            } else if (status != null && doctorId != null && authorizedUser.getRole().equals(Role.DOCTOR)) {
                appointments = service.findByDateAndStatusAndDoctor(date, status, doctorId);
            } else if (status != null) {
                if (!(status.equals("All") || status.equals("Все"))) {
                    appointments = service.findByDateAndStatus(date, status);
                } else {
                    appointments = service.findByTime(date);
                }
            } else {
                appointments = service.findByTime(date);
            }
        } else {
            appointments = service.findAll();
        }

        Forward forward = new Forward("/appointment/doctor/choice.html");
        forward.getAttributes().put("appointments", appointments);
        forward.setRedirect(true);
        return forward;
        //TODO different
    }
}
