package controller.action.admin;

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
            Integer id = (Integer) request.getAttribute("id");
            if (id == null) {
                id = Integer.parseInt(request.getParameter("id"));
            }
            VacationService service = serviceFactory.getVacationService();
            try {
                Vacation vacation = service.findById(id);
                request.setAttribute("vacation", vacation);
            } catch (ServicePersistentException e) {
                logger.error(e);
            }
            request.setAttribute("id", id);
        } catch (NumberFormatException e) {
        }
        return null;
    }
}
