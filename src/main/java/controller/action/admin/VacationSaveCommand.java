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
            Validator<Doctor> doctorValidator = validatorFactory.createDoctorValidator();
            Vacation vacation = validator.validate(request);
            Doctor doctorFromRequest = doctorValidator.validate(request);

            VacationService vacationService = serviceFactory.getVacationService();
            DoctorService doctorService = serviceFactory.getDoctorService();

            Doctor doctor;
            if (vacation.getId() != null) {
                doctor = doctorService.findById(vacation.getId());
            } else {
                //TODO can be doctors with the same name and surname
                doctor = doctorService.findBySurnameAndName(doctorFromRequest.getSurname(), doctorFromRequest.getName());
                vacation.setId(doctor.getId());
            }
            if (doctor != null) {
                vacation.setDoctor(doctor);
                vacationService.save(vacation);
                forward.getAttributes().put("id", vacation.getId());
                forward.getAttributes().put("message", "Данные об отпуске успешно сохранены");
            } else {
                forward.getAttributes().put("message", "Данного врача не найдено");
            }
        } catch (IncorrectFormDataException e) {
            e.printStackTrace();
        }
        return forward;
    }
}
