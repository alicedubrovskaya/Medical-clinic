package controller.action.admin.doctor;

import controller.action.admin.AdministratorAction;
import domain.Doctor;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import service.DoctorService;
import validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoctorSaveAction extends AdministratorAction {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward("/doctor/edit.html");
        try {
            Validator<Doctor> validator = validatorFactory.createDoctorValidator();
            Doctor doctor = validator.validate(request);
            DoctorService service = serviceFactory.getDoctorService();
            //TODO login, password - they are empty
            service.save(doctor);
            forward.getAttributes().put("id", doctor.getId());
            forward.getAttributes().put("message", "Данные врача успешно сохранены");

        } catch (IncorrectFormDataException e) {
            e.printStackTrace();
        }
        return forward;
    }
}
