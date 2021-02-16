package controller.action.admin;

import controller.enumeration.AttributeType;
import controller.enumeration.CommandType;
import controller.enumeration.ParameterType;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.DoctorService;
import service.util.ResourceBundleUtil;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

/**
 * Command for doctor's deleting
 */
public class DoctorDeleteCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(DoctorDeleteCommand.class);
    private static final String HTML = ".html";
    private static final String SUCCESSFUL_DELETING = "message.doctor.deleted";

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward(CommandType.DOCTOR_LIST.getCommand() + HTML);
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
        try {
            DoctorService service = serviceFactory.getDoctorService();
            Integer id = Integer.parseInt(request.getParameter(ParameterType.ID.getValue()));
            service.delete(id);
            forward.getAttributes().put(AttributeType.MESSAGE.getValue(), rb.getString(SUCCESSFUL_DELETING));
        } catch (NumberFormatException | ServicePersistentException e) {
            logger.error(e);
        }
        return forward;
    }
}
