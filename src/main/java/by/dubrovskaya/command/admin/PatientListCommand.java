package by.dubrovskaya.command.admin;

import by.dubrovskaya.command.Command;
import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.Patient;
import by.dubrovskaya.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.dubrovskaya.service.PatientService;
import by.dubrovskaya.service.util.ResourceBundleUtil;
import by.dubrovskaya.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ResourceBundle;

public class PatientListCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(PatientListCommand.class);
    private static final String PATIENTS_NOT_FOUND = "message.patient.notFound";

    @Override
    public Command.Forward exec(HttpServletRequest request, HttpServletResponse response) {
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
