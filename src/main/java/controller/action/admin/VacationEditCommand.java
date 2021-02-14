package controller.action.admin;

import controller.enumeration.AttributeType;
import controller.enumeration.ParameterType;
import domain.Vacation;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.VacationService;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VacationEditCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(VacationEditCommand.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        try {
            Integer id = (Integer) request.getAttribute(AttributeType.ID.getValue());
            if (id == null) {
                id = Integer.parseInt(request.getParameter(ParameterType.ID.getValue()));
            }
            VacationService service = serviceFactory.getVacationService();
            Vacation vacation = service.findById(id);
            request.setAttribute(AttributeType.VACATION.getValue(), vacation);
            request.setAttribute(AttributeType.ID.getValue(), id);
        } catch (NumberFormatException | ServicePersistentException e) {
            logger.error(e);
        }
        return null;
    }
}
