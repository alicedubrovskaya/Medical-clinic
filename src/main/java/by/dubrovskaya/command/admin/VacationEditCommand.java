package by.dubrovskaya.command.admin;

import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.enumeration.ParameterType;
import by.dubrovskaya.domain.Vacation;
import by.dubrovskaya.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.dubrovskaya.service.VacationService;
import by.dubrovskaya.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VacationEditCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(VacationEditCommand.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response){
        try {
            Integer id = (Integer) request.getAttribute(AttributeType.ID.getValue());
            if (id == null) {
                id = Integer.parseInt(request.getParameter(ParameterType.ID.getValue()));
            }
            VacationService service = serviceFactory.getVacationService();
            Vacation vacation = service.findById(id);
            request.setAttribute(AttributeType.VACATION.getValue(), vacation);
            request.setAttribute(AttributeType.ID.getValue(), id);
        } catch (NumberFormatException | ServicePersistentException e) {
            logger.error(e);
        }
        return null;
    }
}
