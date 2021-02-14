package controller.action.admin;

import controller.enumeration.AttributeType;
import controller.enumeration.CommandType;
import domain.Doctor;
import domain.User;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.DoctorService;
import service.exception.ServicePersistentException;
import validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoctorSaveCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(DoctorSaveCommand.class);
    private static final String HTML = ".html";

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = null;
        try {
            DoctorService service = serviceFactory.getDoctorService();

            Validator<Doctor> validator = validatorFactory.createDoctorValidator();
            Doctor doctor = validator.validate(request);

            if (doctor.getId() != null) {
                service.save(doctor);
                forward = new Forward(CommandType.DOCTOR_LIST.getCommand() + HTML);
                forward.getAttributes().put(AttributeType.ID.getValue(), doctor.getId());
                forward.getAttributes().put("message", "Данные врача успешно обновлены");
            } else {
                Validator<User> userValidator = validatorFactory.createUserValidator();
                User user = userValidator.validate(request);
                if (user != null) {
                    doctor.setLogin(user.getLogin());
                    doctor.setPassword(user.getPassword());

                    service.save(doctor);
                    forward = new Forward(CommandType.DOCTOR_LIST.getCommand() + HTML);
                    forward.getAttributes().put(AttributeType.ID.getValue(), doctor.getId());
                    forward.getAttributes().put("message", "Данные врача успешно сохранены");
                }
            }
        } catch (IncorrectFormDataException | ServicePersistentException e) {
            logger.error(e);
        }
        return forward;
    }
}
