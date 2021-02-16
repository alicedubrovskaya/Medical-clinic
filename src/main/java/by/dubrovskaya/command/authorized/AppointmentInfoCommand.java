package by.dubrovskaya.command.authorized;

import by.dubrovskaya.command.Command;
import by.dubrovskaya.command.admin.GenerateAppointmentsCommand;
import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.Appointment;
import by.dubrovskaya.exception.PersistentException;
import by.dubrovskaya.exception.ServicePersistentException;
import by.dubrovskaya.service.AppointmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppointmentInfoCommand extends AuthorizedUserCommand {
    private static final Logger logger = LogManager.getLogger(AppointmentInfoCommand.class);

    @Override
    public Command.Forward exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Integer id = (Integer) request.getAttribute(AttributeType.ID.getValue());
            if (id == null) {
                id = Integer.parseInt(request.getParameter("id"));
            }
            AppointmentService service = serviceFactory.getAppointmentService();
            Appointment appointment = service.findById(id);
            if (appointment != null) {
                request.setAttribute(AttributeType.APPOINTMENT.getValue(), appointment);
            }
        } catch (NumberFormatException | ServicePersistentException e) {
            logger.error(e);
        }
        return null;
    }
}
