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

public class VacationSaveCommand extends AdministratorCommand {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward("/vacation/list.html");
        try {
            Validator<Vacation> validator = validatorFactory.createVacationValidator();
            Vacation vacation = validator.validate(request);

            VacationService vacationService = serviceFactory.getVacationService();
            DoctorService doctorService = serviceFactory.getDoctorService();

            Doctor doctor = doctorService.findById(vacation.getId());
            vacation.setDoctor(doctor);
            if (vacation.getStart().getTime() < vacation.getEnd().getTime()) {
                vacationService.save(vacation);
                forward.getAttributes().put("id", vacation.getId());
                forward.getAttributes().put("message", "Данные об отпуске успешно сохранены");
            } else {
                forward.getAttributes().put("message", "Данные отпуска некорректны");
            }

        } catch (IncorrectFormDataException e) {
            e.printStackTrace();
        }
        return forward;
    }
}
