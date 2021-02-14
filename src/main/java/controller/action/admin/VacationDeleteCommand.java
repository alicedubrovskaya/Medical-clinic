package controller.action.admin;

import controller.enumeration.CommandType;
import controller.enumeration.ParameterType;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.VacationService;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VacationDeleteCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(VacationDeleteCommand.class);
    private static final String HTML = ".html";

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward(CommandType.VACATION_LIST.getCommand() + HTML);
        try {
            VacationService service = serviceFactory.getVacationService();
            Integer id = Integer.parseInt(request.getParameter(ParameterType.ID.getValue()));
            service.delete(id);
            forward.getAttributes().put("message", "Отпуск врача успешно удалён");
        } catch (NumberFormatException | ServicePersistentException e) {
            logger.error(e);
        }
        return forward;
    }
}
