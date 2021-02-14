package controller.action.authorized;

import controller.action.Command;
import controller.enumeration.AttributeType;
import controller.enumeration.CommandType;
import controller.enumeration.ParameterType;
import domain.Appointment;
import domain.User;
import domain.enumeration.Role;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AppointmentService;
import validator.DateValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

public class AppointmentListCommand extends AuthorizedUserCommand {
    private static final String HTML = ".html";
    private static final String ALL_RUSSIAN = "Все";
    private static final String ALL_ENGLISH = "All";
    private static final Logger logger = LogManager.getLogger(AppointmentListCommand.class);

    @Override
    public Command.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        HttpSession session = request.getSession(false);
        User authorizedUser = (User) session.getAttribute(AttributeType.USER_AUTHORIZED.getValue());
        Forward forward = new Forward(CommandType.APPOINTMENT_CHOICE.getCommand() + HTML);

        DateValidator validator = validatorFactory.createDateValidator();
        Date date;
        String specialization;
        String status;
        Integer doctorId;
        try {
            date = validator.validateDate(request);
            specialization = request.getParameter(ParameterType.SPECIALIZATION.getValue());
            status = request.getParameter(ParameterType.STATUS.getValue());
            if (request.getParameter(ParameterType.DOCTOR_ID.getValue()) != null) {
                doctorId = Integer.valueOf(request.getParameter(ParameterType.DOCTOR_ID.getValue()));
            } else {
                doctorId = authorizedUser.getId();
            }

            AppointmentService service = serviceFactory.getAppointmentService();
            List<Appointment> appointments;

            //TODO
            if (date != null) {
                if (specialization != null) {
                    appointments = service.findByTimeAndSpecialization(date, specialization);
                } else if (!status.equals(ALL_ENGLISH) && !status.equals(ALL_RUSSIAN) && doctorId != null
                        && authorizedUser.getRole().equals(Role.DOCTOR)) {
                    appointments = service.findByDateAndStatusAndDoctor(date, status, doctorId);
                } else {
                    if (!(status.equals(ALL_ENGLISH) || status.equals(ALL_RUSSIAN))) {
                        appointments = service.findByDateAndStatus(date, status);
                    } else {
                        appointments = service.findByTime(date);
                    }
                }
            } else {
                appointments = service.findAll();
            }
            forward.getAttributes().put(AttributeType.APPOINTMENTS.getValue(), appointments);
        } catch (IncorrectFormDataException e) {
            logger.error(e);
        }
        forward.setRedirect(true);
        return forward;
    }
}
