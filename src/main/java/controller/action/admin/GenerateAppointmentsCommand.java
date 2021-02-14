package controller.action.admin;

import controller.action.Command;
import controller.enumeration.CommandType;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import service.AppointmentService;
import validator.DateValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class GenerateAppointmentsCommand extends AdministratorCommand {
    private static final String HTML = ".html";

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Command.Forward forward;
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

        if (calendar != null && countOfDays != 0) {
            forward = new Command.Forward(CommandType.APPOINTMENT_LIST.getCommand() + HTML);
            service.createAppointmentsForDoctors(calendar, countOfDays);
            forward.getAttributes().put("message", "Расписание врачей успешно сгенерировано");
        } else {
            return null;
        }
        return forward;
    }
}
