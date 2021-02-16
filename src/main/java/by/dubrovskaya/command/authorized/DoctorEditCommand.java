package by.dubrovskaya.command.authorized;

import by.dubrovskaya.command.Command;
import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.enumeration.ParameterType;
import by.dubrovskaya.domain.Doctor;
import by.dubrovskaya.domain.User;
import by.dubrovskaya.domain.enumeration.Shift;
import by.dubrovskaya.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.dubrovskaya.service.DoctorService;
import by.dubrovskaya.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class DoctorEditCommand extends AuthorizedUserCommand {
    private static final Logger logger = LogManager.getLogger(DoctorEditCommand.class);

    @Override
    public Command.Forward exec(HttpServletRequest request, HttpServletResponse response){
        DoctorService service = serviceFactory.getDoctorService();
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

                Doctor doctor = service.findById(id);
                request.setAttribute(AttributeType.DOCTOR.getValue(), doctor);
            } else {
                request.setAttribute(AttributeType.USER.getValue(), user);
            }

            request.setAttribute(AttributeType.SPECIALIZATIONS.getValue(), service.findAllSpecializations());
            request.setAttribute(AttributeType.WORKING_SHIFTS.getValue(),
                    Arrays.asList(Shift.FIRST.getName(), Shift.SECOND.getName())
            );
        } catch (NumberFormatException | ServicePersistentException e) {
            logger.error(e);
        }
        return null;
    }
}
