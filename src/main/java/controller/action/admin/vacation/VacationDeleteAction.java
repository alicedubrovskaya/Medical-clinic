package controller.action.admin.vacation;

import controller.action.admin.AdministratorAction;
import exception.PersistentException;
import service.UserService;
import service.VacationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VacationDeleteAction extends AdministratorAction {
    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward("/vacation/list.html");
        try {
            VacationService service = serviceFactory.getVacationService();
            Integer id= Integer.parseInt(request.getParameter("id"));
            service.delete(id);
            forward.getAttributes().put("message", "Отпуск врача успешно удалён");
        } catch (NumberFormatException e) {
        }
        return forward;
    }
}
