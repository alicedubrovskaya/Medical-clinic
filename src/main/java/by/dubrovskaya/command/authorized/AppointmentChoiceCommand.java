package by.dubrovskaya.command.authorized;

import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.enumeration.Status;
import by.dubrovskaya.exception.ServicePersistentException;
import by.dubrovskaya.service.DoctorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class AppointmentChoiceCommand extends AuthorizedUserCommand {
    private static final Logger logger = LogManager.getLogger(AppointmentChoiceCommand.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        List<String> statuses = Arrays.asList(Status.WAS.getName(), Status.MISSED.getName());
        request.setAttribute(AttributeType.STATUSES.getValue(), statuses);

        DoctorService service = serviceFactory.getDoctorService();
        try {
            List<String> specializations = service.findAllSpecializations();
            request.setAttribute(AttributeType.SPECIALIZATIONS.getValue(), specializations);
        } catch (ServicePersistentException e) {
            logger.error(e);
        }
        return null;
    }
}
