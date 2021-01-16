package controller.action.admin;

import domain.Doctor;
import domain.Vacation;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import service.DoctorService;
import service.VacationService;
import validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VacationSaveAction extends AdministratorAction {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward("/vacation/edit.html");
        try {
            Validator<Vacation> validator = validatorFactory.createVacationValidator();
            Vacation vacation = validator.validate(request);

            VacationService vacationService = serviceFactory.getVacationService();
            DoctorService doctorService = serviceFactory.getDoctorService();

            Doctor doctor = doctorService.findById(vacation.getId());
            vacation.setDoctor(doctor);
            vacationService.save(vacation);

            forward.getAttributes().put("id", vacation.getId());
            forward.getAttributes().put("message", "Данные пользователя успешно сохранены");

        } catch (IncorrectFormDataException e) {
            e.printStackTrace();
        }
        return forward;
    }
}
