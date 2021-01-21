package controller.action.admin.vacation;

import controller.action.admin.AdministratorCommand;
import domain.Vacation;
import exception.PersistentException;
import service.VacationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VacationEditCommand extends AdministratorCommand {
    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        try {
            Integer id = (Integer) request.getAttribute("id");
            if (id == null) {
                id = Integer.parseInt(request.getParameter("id"));
            }
            VacationService service = serviceFactory.getVacationService();
            Vacation vacation = service.findById(id);
            if (vacation != null) {
                request.setAttribute("vacation", vacation);
            }
            request.setAttribute("id", id);
        } catch (NumberFormatException e) {
        }
        return null;
    }
}
