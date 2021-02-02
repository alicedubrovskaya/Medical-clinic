package controller.action;

import domain.Patient;
import domain.User;
import domain.enumeration.Role;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.PatientService;
import service.exception.ServicePersistentException;
import service.impl.DoctorServiceImpl;
import validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class PatientSaveCommand extends Command {
    private static final Logger logger = LogManager.getLogger(PatientSaveCommand.class);

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = null;
        try {
            PatientService service = serviceFactory.getPatientService();

            Validator<Patient> validator = validatorFactory.createPatientValidator();
            Patient patient = validator.validate(request);

            if (patient.getId() != null) {
                service.save(patient);
                forward = new Forward("/patient/edit.html");
                forward.getAttributes().put("id", patient.getId());
                forward.getAttributes().put("message", "Данные пациента успешно обновлены");
            } else {
                Validator<User> userValidator = validatorFactory.createUserValidator();
                User user = userValidator.validate(request);
                if (user != null) {
                    patient.setLogin(user.getLogin());
                    patient.setPassword(user.getPassword());
                    service.save(patient);
                    forward = new Forward("/login.html");
                    forward.getAttributes().put("id", patient.getId());
                    forward.getAttributes().put("message", "Данные пациента успешно сохранены");
                }
            }
        } catch (IncorrectFormDataException | ServicePersistentException e) {
            logger.error(e);
        }
        return forward;
    }
}
