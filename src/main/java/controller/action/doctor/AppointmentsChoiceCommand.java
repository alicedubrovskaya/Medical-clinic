package controller.action.doctor;

import exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class AppointmentsChoiceCommand extends DoctorCommand {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        List<String> statuses = Arrays.asList("Был", "Не был");
        request.setAttribute("statuses", statuses);
        return null;
    }
}
