package controller.action.admin;

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

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = null;
        try {
            DoctorService service = serviceFactory.getDoctorService();

            Validator<Doctor> validator = validatorFactory.createDoctorValidator();
            Doctor doctor = validator.validate(request);

            if (doctor.getId() != null) {
                try {
                    service.save(doctor);
                    forward = new Forward("/doctor/list.html");
                    forward.getAttributes().put("id", doctor.getId());
                    forward.getAttributes().put("message", "Данные врача успешно обновлены");
                } catch (ServicePersistentException e) {
                    logger.info("Doctor with id={} wasn't update", doctor.getId());
                }
            } else {
                Validator<User> userValidator = validatorFactory.createUserValidator();
                User user = userValidator.validate(request);
                if (user != null) {
                    doctor.setLogin(user.getLogin());
                    doctor.setPassword(user.getPassword());
                    try {
                        service.save(doctor);
                        forward = new Forward("/doctor/list.html");
                        forward.getAttributes().put("id", doctor.getId());
                        forward.getAttributes().put("message", "Данные врача успешно сохранены");
                    } catch (ServicePersistentException e) {
                        logger.info("Doctor wasn't save");
                    }
                }
            }
        } catch (IncorrectFormDataException e) {
            e.printStackTrace();
        }
        return forward;
    }
}
