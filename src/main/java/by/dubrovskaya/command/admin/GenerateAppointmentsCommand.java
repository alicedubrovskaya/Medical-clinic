package by.dubrovskaya.command.admin;

import by.dubrovskaya.command.Command;
import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.enumeration.CommandType;
import by.dubrovskaya.exception.IncorrectFormDataException;
import by.dubrovskaya.exception.ServicePersistentException;
import by.dubrovskaya.service.AppointmentService;
import by.dubrovskaya.service.validator.DateValidator;
import by.dubrovskaya.service.util.ResourceBundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.ResourceBundle;

public class GenerateAppointmentsCommand extends AdministratorCommand {
    private static final String HTML = ".html";
    private static final String SUCCESSFUL_GENERATING_OF_APPOINTMENTS = "message.appointment.generated";
    private static final Logger logger = LogManager.getLogger(GenerateAppointmentsCommand.class);

    @Override
    public Command.Forward exec(HttpServletRequest request, HttpServletResponse response) {
        Command.Forward forward = null;
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        AppointmentService service = serviceFactory.getAppointmentService();
        DateValidator validator = validatorFactory.createDateValidator();
        Date calendar = null;
        int countOfDays = 0;
        try {
            calendar = validator.validateDate(request);
            countOfDays = validator.validateDays(request);


            if (calendar != null && countOfDays != 0) {
                forward = new Command.Forward(CommandType.APPOINTMENT_LIST.getCommand() + HTML);
                service.createAppointmentsForDoctors(calendar, countOfDays);
                forward.getAttributes().put(AttributeType.MESSAGE.getValue(), rb.getString(SUCCESSFUL_GENERATING_OF_APPOINTMENTS));
            } else {
                return null;
            }
        } catch (IncorrectFormDataException | ServicePersistentException e) {
            logger.error(e);
        }
        return forward;
    }
}
