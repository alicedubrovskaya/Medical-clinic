package by.dubrovskaya.command.authorized;

import by.dubrovskaya.command.Command;
import by.dubrovskaya.service.validator.DateValidator;
import by.dubrovskaya.domain.Appointment;
import by.dubrovskaya.domain.User;
import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.enumeration.CommandType;
import by.dubrovskaya.domain.enumeration.ParameterType;
import by.dubrovskaya.domain.enumeration.Role;
import by.dubrovskaya.exception.IncorrectFormDataException;
import by.dubrovskaya.exception.ServicePersistentException;
import by.dubrovskaya.service.AppointmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public Command.Forward exec(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        User authorizedUser = (User) session.getAttribute(AttributeType.USER_AUTHORIZED.getValue());
        Command.Forward forward = new Command.Forward(CommandType.APPOINTMENT_CHOICE.getCommand() + HTML);

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

            //TODO remove eng russ (already in value exists)
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
        } catch (IncorrectFormDataException | ServicePersistentException e) {
            logger.error(e);
        }
        forward.setRedirect(true);
        return forward;
    }
}
