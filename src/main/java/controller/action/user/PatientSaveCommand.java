package controller.action.user;

import controller.action.Command;
import controller.enumeration.AttributeType;
import controller.enumeration.CommandType;
import domain.Patient;
import domain.User;
import domain.enumeration.Role;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.PatientService;
import service.ResourceBundleUtil;
import service.exception.ServicePersistentException;
import controller.validator.Validator;

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
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = null;
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        try {
            PatientService service = serviceFactory.getPatientService();

            Validator<Patient> validator = validatorFactory.createPatientValidator();
            Patient patient = validator.validate(request);

            if (patient.getId() != null) {
                service.save(patient);
                forward = new Forward(CommandType.PATIENT_EDIT.getCommand() + HTML);
                forward.getAttributes().put(AttributeType.ID.getValue(), patient.getId());
                forward.getAttributes().put(AttributeType.MESSAGE.getValue(), SUCCESSFUL_UPDATING);
            } else {
                Validator<User> userValidator = validatorFactory.createUserValidator();
                User user = userValidator.validate(request);
                if (user != null) {
                    patient.setLogin(user.getLogin());
                    patient.setPassword(user.getPassword());
                    service.save(patient);
                    forward = new Forward(CommandType.LOGIN.getCommand() + HTML);
                    forward.getAttributes().put(AttributeType.ID.getValue(), patient.getId());
                    forward.getAttributes().put(AttributeType.MESSAGE.getValue(), SUCCESSFUL_UPDATING);
                }
            }
        } catch (IncorrectFormDataException | ServicePersistentException e) {
            logger.error(e);
        }
        return forward;
    }
}
