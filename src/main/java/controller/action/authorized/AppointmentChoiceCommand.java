package controller.action.authorized;

import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.DoctorService;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class AppointmentChoiceCommand extends AuthorizedUserCommand {
    private static final Logger logger = LogManager.getLogger(AppointmentChoiceCommand.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        List<String> statuses = Arrays.asList("Был", "Не был");
        request.setAttribute("statuses", statuses);

        DoctorService service = serviceFactory.getDoctorService();
        try {
            List<String> specializations = service.findAllSpecializations();
            request.setAttribute("specializations", specializations);
        } catch (ServicePersistentException e) {
            logger.error(e);
        }
        return null;
    }
}
