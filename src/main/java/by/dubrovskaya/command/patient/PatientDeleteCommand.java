package by.dubrovskaya.command.patient;

import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.enumeration.CommandType;
import by.dubrovskaya.domain.enumeration.ParameterType;
import by.dubrovskaya.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.dubrovskaya.service.PatientService;
import by.dubrovskaya.service.util.ResourceBundleUtil;
import by.dubrovskaya.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

public class PatientDeleteCommand extends PatientCommand {
    private static final Logger logger = LogManager.getLogger(PatientDeleteCommand.class);
    private static final String HTML = ".html";
    private static final String SUCCESSFUL_DELETING = "message.patient.deleted";

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
        Forward forward = new Forward(CommandType.LOGOUT.getCommand() + HTML);
        try {
            PatientService service = serviceFactory.getPatientService();
            Integer id = Integer.parseInt(request.getParameter(ParameterType.ID.getValue()));
            service.delete(id);
            forward.getAttributes().put(AttributeType.MESSAGE.getValue(), rb.getString(SUCCESSFUL_DELETING));
        } catch (NumberFormatException | ServicePersistentException e) {
            logger.error(e);
        }
        return forward;
    }
}
