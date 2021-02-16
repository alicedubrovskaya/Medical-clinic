package by.dubrovskaya.command.admin;

import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.Vacation;
import by.dubrovskaya.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.dubrovskaya.service.VacationService;
import by.dubrovskaya.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class VacationListCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(VacationListCommand.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        VacationService service = serviceFactory.getVacationService();
        try {
            List<Vacation> vacations = service.findAll();
            request.setAttribute(AttributeType.VACATIONS.getValue(), vacations);
        } catch (ServicePersistentException e) {
            logger.info(e);
        }
        return null;
    }
}
