package controller.action.admin;

import controller.action.Action;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import service.AppointmentService;
import validator.DateValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

public class GenerateAppointmentsAction extends AdministratorAction {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Action.Forward forward = new Action.Forward("/appointment/list.html");
        AppointmentService service = serviceFactory.getAppointmentService();


        DateValidator validator = validatorFactory.createDateValidator();
        Date calendar = null;
        int countOfDays = 0;
        try {
            calendar = validator.validateDate(request);
            countOfDays = validator.validateDays(request);
        } catch (IncorrectFormDataException e) {
            e.printStackTrace();
        }

        service.createAppointmentsForDoctors(calendar, countOfDays);
        forward.getAttributes().put("message", "Расписание врачей успешно сгенерировано");
        return forward;
    }
}
