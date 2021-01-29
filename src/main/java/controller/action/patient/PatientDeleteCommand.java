package controller.action.patient;

import exception.PersistentException;
import service.PatientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PatientDeleteCommand extends PatientCommand {
    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward("/logout.html");
        try {
            PatientService service = serviceFactory.getPatientService();
            Integer id = Integer.parseInt(request.getParameter("id"));
            service.delete(id);
            forward.getAttributes().put("message", "Пациент успешно удалён");
        } catch (NumberFormatException e) {
        }
        return forward;
    }
}
