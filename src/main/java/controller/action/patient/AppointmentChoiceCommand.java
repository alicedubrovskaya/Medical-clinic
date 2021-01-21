package controller.action.patient;

import exception.PersistentException;
import service.DoctorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AppointmentChoiceCommand extends PatientCommand {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        DoctorService service = serviceFactory.getDoctorService();
        List<String> specializations = service.findAllSpecializations();
        request.setAttribute("specializations", specializations);
        return null;
    }
}
