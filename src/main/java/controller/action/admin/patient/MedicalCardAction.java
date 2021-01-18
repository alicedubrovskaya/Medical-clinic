package controller.action.admin.patient;

import controller.action.patient.PatientAction;
import domain.Appointment;
import domain.Doctor;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import service.AppointmentService;
import service.DoctorService;
import validator.DateValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class MedicalCardAction extends PatientAction {
    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        AppointmentService service = serviceFactory.getAppointmentService();
        Integer id = getAuthorizedUser().getId();
        List<Appointment> appointments = service.findByPatient(id);
        request.setAttribute("appointments", appointments);
        return null;
    }
}
