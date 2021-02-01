package controller.action.admin;

import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.VacationService;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VacationDeleteCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(VacationDeleteCommand.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward("/vacation/list.html");
        try {
            VacationService service = serviceFactory.getVacationService();
            Integer id = Integer.parseInt(request.getParameter("id"));
            try {
                service.delete(id);
                forward.getAttributes().put("message", "Отпуск врача успешно удалён");
            } catch (ServicePersistentException e) {
                logger.error(e);
            }
        } catch (NumberFormatException e) {
        }
        return forward;
    }
}
