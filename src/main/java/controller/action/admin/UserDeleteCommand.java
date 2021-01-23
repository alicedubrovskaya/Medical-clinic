package controller.action.admin;

import controller.action.admin.AdministratorCommand;
import exception.PersistentException;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserDeleteCommand extends AdministratorCommand {
    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward("/user/list.html");
        try {
            UserService service = serviceFactory.getUserService();
            Integer id= Integer.parseInt(request.getParameter("id"));
            service.delete(id);
            forward.getAttributes().put("message", "Пользователь успешно удалён");
        } catch (NumberFormatException e) {
        }
        return forward;
    }
}