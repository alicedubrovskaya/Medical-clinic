package controller.action.authorized;

import controller.action.admin.DoctorDeleteCommand;
import domain.Doctor;
import domain.User;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.DoctorService;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class DoctorEditCommand extends AuthorizedUserCommand {
    private static final Logger logger = LogManager.getLogger(DoctorDeleteCommand.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        DoctorService service = serviceFactory.getDoctorService();
        HttpSession session = request.getSession(false);
        User authorizedUser = (User) session.getAttribute("authorizedUser");
        try {
            User user = (User) request.getAttribute("user");
            if (user == null) {
                Integer id;
                String parameter = request.getParameter("id");
                if (parameter != null) {
                    id = Integer.parseInt(parameter);
                } else {
                    id = authorizedUser.getId();
                }

                try {
                    Doctor doctor = service.findById(id);
                    request.setAttribute("doctor", doctor);
                } catch (ServicePersistentException e) {
                    logger.error("Doctor wasn't found");
                }
            } else {
                request.setAttribute("user", user);
            }

            try {
                request.setAttribute("specializations", service.findAllSpecializations());
            } catch (ServicePersistentException e) {
                logger.error("Specializations wasn't found");
            }
            request.setAttribute("workingShifts", Arrays.asList("Первая", "Вторая"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
