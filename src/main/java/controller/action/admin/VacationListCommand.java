package controller.action.admin;

import domain.Vacation;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.VacationService;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class VacationListCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(VacationListCommand.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        VacationService service = serviceFactory.getVacationService();
        try {
            List<Vacation> vacations = service.findAll();
            request.setAttribute("vacations", vacations);
        } catch (ServicePersistentException e) {
            logger.info(e);
        }
        return null;
    }
}
