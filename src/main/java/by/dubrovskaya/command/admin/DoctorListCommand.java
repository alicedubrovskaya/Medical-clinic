package by.dubrovskaya.command.admin;

import by.dubrovskaya.command.Command;
import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.Doctor;
import by.dubrovskaya.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.dubrovskaya.service.DoctorService;
import by.dubrovskaya.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DoctorListCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(DoctorListCommand.class);

    @Override
    public Command.Forward exec(HttpServletRequest request, HttpServletResponse response) {
        DoctorService service = serviceFactory.getDoctorService();
        try {
            List<Doctor> doctors = service.findAll();
            request.setAttribute(AttributeType.DOCTORS.getValue(), doctors);
        } catch (ServicePersistentException e) {
            logger.info(e);
        }
        return null;
    }
}
