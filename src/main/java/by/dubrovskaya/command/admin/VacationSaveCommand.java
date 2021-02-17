package by.dubrovskaya.command.admin;

import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.enumeration.CommandType;
import by.dubrovskaya.domain.Doctor;
import by.dubrovskaya.domain.Vacation;
import by.dubrovskaya.exception.IncorrectFormDataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.dubrovskaya.service.DoctorService;
import by.dubrovskaya.service.util.ResourceBundleUtil;
import by.dubrovskaya.service.VacationService;
import by.dubrovskaya.exception.ServicePersistentException;
import by.dubrovskaya.service.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

public class VacationSaveCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(VacationSaveCommand.class);
    private static final String HTML = ".html";
    private static final String SUCCESSFUL_SAVING = "message.vacation.saved";
    private static final String DOCTOR_NOT_FOUND = "message.doctor.notFound";

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
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
                //doctors cannot be with the same name and surname
                doctor = doctorService.findBySurnameAndName(doctorFromRequest.getSurname(), doctorFromRequest.getName());
                vacation.setId(doctor.getId());
            }
            if (doctor != null) {
                vacation.setDoctor(doctor);
                vacationService.save(vacation);
                forward.getAttributes().put(AttributeType.ID.getValue(), vacation.getId());
                forward.getAttributes().put(AttributeType.MESSAGE.getValue(), rb.getString(SUCCESSFUL_SAVING));
            }
        } catch (IncorrectFormDataException | ServicePersistentException e) {
            logger.error(e);
            forward.getAttributes().put(AttributeType.MESSAGE.getValue(), rb.getString(DOCTOR_NOT_FOUND));
        }
        forward.setRedirect(true);
        return forward;
    }
}
