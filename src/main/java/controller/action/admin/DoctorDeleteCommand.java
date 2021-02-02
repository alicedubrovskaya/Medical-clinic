package controller.action.admin;

import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.DoctorService;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoctorDeleteCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(DoctorDeleteCommand.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward("/doctor/list.html");
        try {
            DoctorService service = serviceFactory.getDoctorService();
            Integer id = Integer.parseInt(request.getParameter("id"));
            try {
                service.delete(id);
                forward.getAttributes().put("message", "Врач успешно удалён");
            } catch (ServicePersistentException e) {
                logger.error("Doctor wasn't deleted");
            }
        } catch (NumberFormatException e) {
            logger.error("Incorrect data");
        }
        return forward;
    }
}
