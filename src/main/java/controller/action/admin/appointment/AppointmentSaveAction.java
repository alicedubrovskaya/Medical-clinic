package controller.action.admin.appointment;

import controller.action.admin.AdministratorAction;
import domain.Appointment;
import domain.Doctor;
import domain.Patient;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import service.AppointmentService;
import service.DoctorService;
import service.PatientService;
import validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppointmentSaveAction extends AdministratorAction {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward("/appointment/list.html");

        Integer patientId = (Integer) request.getAttribute("patientId");
        if (patientId == null) {
            patientId = Integer.parseInt(request.getParameter("patientId"));
        }

        Integer appointmentId = (Integer) request.getAttribute("appointmentId");
        if (appointmentId == null) {
            appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
        }

        PatientService patientService = serviceFactory.getPatientService();
        Patient patient = patientService.findById(patientId);
        AppointmentService appointmentService = serviceFactory.getAppointmentService();
        Appointment appointment = appointmentService.findById(appointmentId);
        appointment.setPatient(patient);

        appointmentService.save(appointment);
        forward.getAttributes().put("message", "Запись к врачу успешно выполнена");

        return forward;
    }
}
