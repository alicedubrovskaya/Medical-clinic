package by.dubrovskaya.command.authorized;

import by.dubrovskaya.domain.Appointment;
import by.dubrovskaya.domain.User;
import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.enumeration.ParameterType;
import by.dubrovskaya.domain.enumeration.Role;
import by.dubrovskaya.exception.ServicePersistentException;
import by.dubrovskaya.service.AppointmentService;
import by.dubrovskaya.service.util.ResourceBundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ResourceBundle;

public class PatientCurrentAppointmentsCommand extends AuthorizedUserCommand {
    private static final Logger logger = LogManager.getLogger(PatientCurrentAppointmentsCommand.class);
    private static final String DATA_NOT_FOUND = "message.data.notFound";
    private static final String WAS_NOT = "Не был";


    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        AppointmentService appointmentService = serviceFactory.getAppointmentService();
        String parameter = request.getParameter(ParameterType.ID.getValue());
        Integer id = null;
        if (parameter == null) {
            HttpSession session = request.getSession(false);
            User authorizedUser = (User) session.getAttribute(AttributeType.USER_AUTHORIZED.getValue());
            if (authorizedUser.getRole().equals(Role.PATIENT)) {
                id = authorizedUser.getId();
            }
        } else {
            id = Integer.parseInt(parameter);
        }

        if (id != null) {
            try {
                List<Appointment> appointments = appointmentService.findByPatientAndStatus(id, WAS_NOT);
                request.setAttribute(AttributeType.APPOINTMENTS.getValue(), appointments);
            } catch (ServicePersistentException e) {
                logger.error(e);
                request.setAttribute(AttributeType.MESSAGE.getValue(), rb.getString(DATA_NOT_FOUND));
            }
        }
        return null;
    }
}
