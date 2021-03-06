package by.dubrovskaya.command.user;

import by.dubrovskaya.command.Command;
import by.dubrovskaya.domain.Patient;
import by.dubrovskaya.domain.User;
import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.enumeration.CommandType;
import by.dubrovskaya.domain.enumeration.Role;
import by.dubrovskaya.exception.IncorrectFormDataException;
import by.dubrovskaya.exception.ServicePersistentException;
import by.dubrovskaya.service.PatientService;
import by.dubrovskaya.service.util.ResourceBundleUtil;
import by.dubrovskaya.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;
import java.util.Set;

public class PatientSaveCommand extends Command {
    private static final Logger logger = LogManager.getLogger(PatientSaveCommand.class);
    private static final String HTML = ".html";
    private static final String SUCCESSFUL_UPDATING = "message.patient.updated";
    private static final String SUCCESSFUL_SAVING = "message.patient.saved";

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        Forward forward = new Forward(CommandType.PATIENT_LIST.getCommand() + HTML);
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        try {
            PatientService service = serviceFactory.getPatientService();

            Validator<Patient> validator = validatorFactory.createPatientValidator();
            Patient patient = validator.validate(request);

            if (patient.getId() != null) {
                service.save(patient);
                forward = new Forward(CommandType.PATIENT_EDIT.getCommand() + HTML);
                forward.getAttributes().put(AttributeType.PATIENT.getValue(), patient);
                forward.getAttributes().put(AttributeType.MESSAGE.getValue(), rb.getString(SUCCESSFUL_UPDATING));
            } else {
                Validator<User> userValidator = validatorFactory.createUserValidator();
                User user = userValidator.validate(request);
                if (user != null) {
                    patient.setLogin(user.getLogin());
                    patient.setPassword(user.getPassword());
                    service.save(patient);
                    forward = new Forward(CommandType.LOGIN.getCommand() + HTML);
                    forward.getAttributes().put(AttributeType.ID.getValue(), patient.getId());
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
