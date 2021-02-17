package by.dubrovskaya.command.admin;

import by.dubrovskaya.command.Command;
import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.enumeration.CommandType;
import by.dubrovskaya.service.validator.Validator;
import by.dubrovskaya.domain.Doctor;
import by.dubrovskaya.domain.User;
import by.dubrovskaya.exception.IncorrectFormDataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.dubrovskaya.service.DoctorService;
import by.dubrovskaya.service.util.ResourceBundleUtil;
import by.dubrovskaya.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

public class DoctorSaveCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(DoctorSaveCommand.class);
    private static final String HTML = ".html";
    private static final String SUCCESSFUL_UPDATING = "message.doctor.updated";
    private static final String SUCCESSFUL_SAVING = "message.doctor.saved";

    @Override
    public Command.Forward exec(HttpServletRequest request, HttpServletResponse response) {
        Command.Forward forward = new Command.Forward(CommandType.DOCTOR_LIST.getCommand() + HTML);

        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
        try {
            DoctorService service = serviceFactory.getDoctorService();

            Validator<Doctor> validator = validatorFactory.createDoctorValidator();
            Doctor doctor = validator.validate(request);

            if (doctor.getId() != null) {
                service.save(doctor);
                forward.getAttributes().put(AttributeType.ID.getValue(), doctor.getId());
                forward.getAttributes().put(AttributeType.MESSAGE.getValue(), rb.getString(SUCCESSFUL_UPDATING));
            } else {
                Validator<User> userValidator = validatorFactory.createUserValidator();
                User user = userValidator.validate(request);
                if (user != null) {
                    doctor.setLogin(user.getLogin());
                    doctor.setPassword(user.getPassword());

                    service.save(doctor);
                    forward.getAttributes().put(AttributeType.ID.getValue(), doctor.getId());
                    forward.getAttributes().put(AttributeType.MESSAGE.getValue(), rb.getString(SUCCESSFUL_SAVING));
                }
            }
        } catch (IncorrectFormDataException | ServicePersistentException e) {
            logger.error(e);
        }
        forward.setRedirect(true);
        return forward;
    }
}
