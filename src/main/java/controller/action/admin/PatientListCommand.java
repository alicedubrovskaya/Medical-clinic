package controller.action.admin;

import controller.enumeration.AttributeType;
import domain.Patient;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.PatientService;
import service.util.ResourceBundleUtil;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ResourceBundle;

public class PatientListCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(PatientListCommand.class);
    private static final String PATIENTS_NOT_FOUND = "message.patient.notFound";

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
        PatientService service = serviceFactory.getPatientService();
        try {
            List<Patient> patients = service.findAll();
            request.setAttribute(AttributeType.PATIENTS.getValue(), patients);
        } catch (ServicePersistentException e) {
            logger.error(e);
            request.setAttribute(AttributeType.MESSAGE.getValue(), rb.getString(PATIENTS_NOT_FOUND));
        }
        return null;
    }
}
