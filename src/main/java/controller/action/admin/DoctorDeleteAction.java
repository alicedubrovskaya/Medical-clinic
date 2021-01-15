package controller.action.admin;

import exception.PersistentException;
import service.DoctorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoctorDeleteAction extends AdministratorAction {
    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward("/doctor/list.html");
        try {
            DoctorService service = serviceFactory.getDoctorService();
            Integer id = Integer.parseInt(request.getParameter("id"));
            service.delete(id);
            forward.getAttributes().put("message", "Врач успешно удалён");
        } catch (NumberFormatException e) {
        }
        return forward;
    }
}
