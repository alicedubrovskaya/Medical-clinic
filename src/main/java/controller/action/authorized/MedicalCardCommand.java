package controller.action.authorized;

import domain.Appointment;
import domain.User;
import domain.enumeration.Role;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AppointmentService;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class MedicalCardCommand extends AuthorizedUserCommand {
    private static final Logger logger = LogManager.getLogger(MedicalCardCommand.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        AppointmentService service = serviceFactory.getAppointmentService();
        String parameter = request.getParameter("id");
        Integer id = null;
        if (parameter == null) {
            HttpSession session = request.getSession(false);
            User authorizedUser = (User) session.getAttribute("authorizedUser");
            if (authorizedUser.getRole().equals(Role.PATIENT)) {
                id = authorizedUser.getId();
            }
        } else {
            id = Integer.parseInt(parameter);
        }

        if (id != null) {
            try {
                List<Appointment> appointments = service.findByPatient(id);
                request.setAttribute("appointments", appointments);
            } catch (ServicePersistentException e){
                logger.error(e);
            }
        }
        return null;
    }
}
