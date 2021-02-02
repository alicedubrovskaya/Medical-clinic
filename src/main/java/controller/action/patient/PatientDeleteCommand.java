package controller.action.patient;

import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.PatientService;
import service.exception.ServicePersistentException;
import service.impl.DoctorServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PatientDeleteCommand extends PatientCommand {
    private static final Logger logger = LogManager.getLogger(DoctorServiceImpl.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward("/logout.html");
        try {
            PatientService service = serviceFactory.getPatientService();
            Integer id = Integer.parseInt(request.getParameter("id"));
            service.delete(id);
            forward.getAttributes().put("message", "Пациент успешно удалён");
        } catch (NumberFormatException | ServicePersistentException e) {
            logger.error(e);
        }
        return forward;
    }
}
