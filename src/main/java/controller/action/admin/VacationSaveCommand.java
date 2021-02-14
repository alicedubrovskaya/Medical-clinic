package controller.action.admin;

import controller.enumeration.AttributeType;
import controller.enumeration.CommandType;
import domain.Doctor;
import domain.Vacation;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.DoctorService;
import service.VacationService;
import service.exception.ServicePersistentException;
import validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VacationSaveCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(VacationSaveCommand.class);
    private static final String HTML = ".html";

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward(CommandType.VACATION_LIST.getCommand() + HTML);
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
                forward.getAttributes().put(AttributeType.ID.getValue(), vacation.getId());
                forward.getAttributes().put("message", "Данные об отпуске успешно сохранены");
            }
        } catch (IncorrectFormDataException | ServicePersistentException e) {
            logger.error(e);
        }
        return forward;
    }
}
