package controller.action.admin;

import controller.enumeration.AttributeType;
import controller.enumeration.CommandType;
import controller.enumeration.ParameterType;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.util.ResourceBundleUtil;
import service.VacationService;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

public class VacationDeleteCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(VacationDeleteCommand.class);
    private static final String HTML = ".html";
    private static final String SUCCESSFUL_DELETING = "message.vacation.deleted";

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
        Forward forward = new Forward(CommandType.VACATION_LIST.getCommand() + HTML);
        try {
            VacationService service = serviceFactory.getVacationService();
            Integer id = Integer.parseInt(request.getParameter(ParameterType.ID.getValue()));
            service.delete(id);
            forward.getAttributes().put(AttributeType.MESSAGE.getValue(), rb.getString(SUCCESSFUL_DELETING));
        } catch (NumberFormatException | ServicePersistentException e) {
            logger.error(e);
        }
        return forward;
    }
}
