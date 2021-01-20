package controller.action.admin.doctor;

import controller.action.admin.AdministratorAction;
import domain.Doctor;
import domain.User;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import service.DoctorService;
import validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoctorSaveAction extends AdministratorAction {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = null;
        try {
            DoctorService service = serviceFactory.getDoctorService();

            Validator<Doctor> validator = validatorFactory.createDoctorValidator();
            Doctor doctor = validator.validate(request);

            if (doctor.getId() != null) {
                service.save(doctor);
                forward = new Forward("/doctor/list.html");
                forward.getAttributes().put("id", doctor.getId());
                forward.getAttributes().put("message", "Данные врача успешно обновлены");
            } else {
                Validator<User> userValidator = validatorFactory.createUserValidator();
                User user = userValidator.validate(request);
                if (user != null) {
                    doctor.setLogin(user.getLogin());
                    doctor.setPassword(user.getPassword());
                    service.save(doctor);
                    forward = new Forward("/doctor/list.html");
                    forward.getAttributes().put("id", doctor.getId());
                    forward.getAttributes().put("message", "Данные врача успешно сохранены");
                }
            }

        } catch (IncorrectFormDataException e) {
            e.printStackTrace();
        }
        return forward;
    }
}
