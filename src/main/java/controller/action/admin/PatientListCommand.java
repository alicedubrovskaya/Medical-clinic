package controller.action.admin;

import domain.Patient;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.PatientService;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PatientListCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(PatientListCommand.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        PatientService service = serviceFactory.getPatientService();
        try {
            List<Patient> patients = service.findAll();
            request.setAttribute("patients", patients);
        } catch (ServicePersistentException e) {
            logger.error(e);
            request.setAttribute("message", "Пациенты не найдены");
        }
        return null;
    }
}
