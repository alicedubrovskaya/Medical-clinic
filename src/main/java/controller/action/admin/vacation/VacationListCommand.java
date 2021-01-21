package controller.action.admin.vacation;

import controller.action.admin.AdministratorCommand;
import domain.Vacation;
import exception.PersistentException;
import service.VacationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class VacationListCommand extends AdministratorCommand {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        VacationService service = serviceFactory.getVacationService();
        List<Vacation> vacations = service.findAll();
        request.setAttribute("vacations", vacations);
        return null;
    }
}
