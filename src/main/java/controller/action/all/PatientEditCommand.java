package controller.action.all;

import controller.action.Command;
import controller.enumeration.AttributeType;
import controller.enumeration.ParameterType;
import domain.Patient;
import domain.User;
import domain.enumeration.Role;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.PatientService;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class PatientEditCommand extends Command {
    private static final Logger logger = LogManager.getLogger(PatientEditCommand.class);

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        HttpSession session = request.getSession(false);
        User authorizedUser = (User) session.getAttribute(AttributeType.USER_AUTHORIZED.getValue());
        try {
            User user = (User) request.getAttribute(AttributeType.USER.getValue());
            if (user == null) {
                Integer id;
                String parameter = request.getParameter(ParameterType.ID.getValue());
                if (parameter != null) {
                    id = Integer.parseInt(parameter);
                } else {
                    id = authorizedUser.getId();
                }
                PatientService service = serviceFactory.getPatientService();
                Patient patient = service.findById(id);
                request.setAttribute(AttributeType.PATIENT.getValue(), patient);
            } else {
                request.setAttribute(AttributeType.USER.getValue(), user);
            }
        } catch (NumberFormatException | ServicePersistentException e) {
            logger.error(e);
        }
        return null;
    }
}
