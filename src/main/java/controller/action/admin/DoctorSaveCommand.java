package controller.action.admin;

import controller.enumeration.AttributeType;
import controller.enumeration.CommandType;
import controller.validator.Validator;
import domain.Doctor;
import domain.User;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.DoctorService;
import service.ResourceBundleUtil;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

public class DoctorSaveCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(DoctorSaveCommand.class);
    private static final String HTML = ".html";
    private static final String SUCCESSFUL_UPDATING = "message.doctor.updated";
    private static final String SUCCESSFUL_SAVING = "message.doctor.saved";

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = null;
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        try {
            DoctorService service = serviceFactory.getDoctorService();

            Validator<Doctor> validator = validatorFactory.createDoctorValidator();
            Doctor doctor = validator.validate(request);

            if (doctor.getId() != null) {
                service.save(doctor);
                forward = new Forward(CommandType.DOCTOR_LIST.getCommand() + HTML);
                forward.getAttributes().put(AttributeType.ID.getValue(), doctor.getId());
                forward.getAttributes().put(AttributeType.MESSAGE.getValue(), rb.getString(SUCCESSFUL_UPDATING));
            } else {
                Validator<User> userValidator = validatorFactory.createUserValidator();
                User user = userValidator.validate(request);
                if (user != null) {
                    doctor.setLogin(user.getLogin());
                    doctor.setPassword(user.getPassword());

                    service.save(doctor);
                    forward = new Forward(CommandType.DOCTOR_LIST.getCommand() + HTML);
                    forward.getAttributes().put(AttributeType.ID.getValue(), doctor.getId());
                    forward.getAttributes().put(AttributeType.MESSAGE.getValue(), rb.getString(SUCCESSFUL_SAVING));
                }
            }
        } catch (IncorrectFormDataException | ServicePersistentException e) {
            logger.error(e);
        }
        return forward;
    }
}
