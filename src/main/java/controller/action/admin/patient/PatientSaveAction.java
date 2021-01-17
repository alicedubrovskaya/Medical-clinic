package controller.action.admin.patient;

import controller.action.admin.AdministratorAction;
import domain.Doctor;
import domain.Patient;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import service.DoctorService;
import service.PatientService;
import validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PatientSaveAction extends AdministratorAction {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward("/patient/list.html");
        try {
            Validator<Patient> validator = validatorFactory.createPatientValidator();
            Patient patient = validator.validate(request);
            PatientService service = serviceFactory.getPatientService();
            //TODO login, password - they are empty
            service.save(patient);
            forward.getAttributes().put("id", patient.getId());
            forward.getAttributes().put("message", "Данные пациента успешно сохранены");
        } catch (IncorrectFormDataException e) {
            e.printStackTrace();
        }
        return forward;
    }
}
