package controller.action.admin;

import controller.action.Action;
import exception.PersistentException;
import service.AppointmentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class GenerateAppointmentsAction extends AdministratorAction {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Action.Forward forward = new Action.Forward("/appointment/list.html");

        //TODO validator for specified date
        AppointmentService service = serviceFactory.getAppointmentService();
        Calendar calendar = new GregorianCalendar(2040, 11, 23);
        service.createAppointmentsForDoctors(calendar.getTime(), 1);
        forward.getAttributes().put("message", "Расписание врачей успешно сгенерировано");
        return forward;
    }
}
