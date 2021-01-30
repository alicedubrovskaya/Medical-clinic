package controller.action.admin;

import domain.Doctor;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.DoctorService;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DoctorListCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(DoctorListCommand.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        DoctorService service = serviceFactory.getDoctorService();
        try {
            List<Doctor> doctors = service.findAll();
            request.setAttribute("doctors", doctors);
        } catch (ServicePersistentException e) {
            logger.info("Doctors wasn't find");
        }
        return null;
    }
}
