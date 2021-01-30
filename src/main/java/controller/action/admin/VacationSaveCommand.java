package controller.action.admin;

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

            Doctor doctor = null;
            try {
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
                }
            } catch (ServicePersistentException e) {
                logger.info("Doctor wasn't find");
            }
        } catch (IncorrectFormDataException e) {
            logger.warn("Incorrect data was found", e);
            e.printStackTrace();
        }
        return forward;
    }
}
