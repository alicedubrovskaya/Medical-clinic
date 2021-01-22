package controller.action.authorized;

import domain.Appointment;
import domain.Patient;
import domain.User;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import service.AppointmentService;
import service.PatientService;
import validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AppointmentSaveCommand extends AuthorizedUserCommand {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward("/appointment/list.html");
        HttpSession session = request.getSession(false);
        User authorizedUser = (User) session.getAttribute("authorizedUser");

        Integer patientId = (Integer) request.getAttribute("patientId");
        if (patientId == null) {
            String parameter = request.getParameter("patientId");
            if (parameter != null) {
                patientId = Integer.parseInt(parameter);
            }
        }
        if (patientId == null) {
            patientId = authorizedUser.getId();
        }

        Integer appointmentId = (Integer) request.getAttribute("appointmentId");
        if (appointmentId == null) {
            appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
        }

        AppointmentService appointmentService = serviceFactory.getAppointmentService();
        Appointment appointment = appointmentService.findById(appointmentId);

        String complaints = request.getParameter("complaints");
        if (complaints == null) {
            PatientService patientService = serviceFactory.getPatientService();
            Patient patient = patientService.findById(patientId);
            appointment.setPatient(patient);
        } else {
            Validator<Appointment> validator = validatorFactory.createAppointmentValidator();
            try {
                Appointment appointmentFromRequest = validator.validate(request);
                appointment.setComplaints(appointmentFromRequest.getComplaints());
                appointment.setStatus(appointmentFromRequest.getStatus());
                appointment.setRecommendation(appointmentFromRequest.getRecommendation());
                appointment.setMedicalReport(appointmentFromRequest.getMedicalReport());
            } catch (IncorrectFormDataException e) {
                e.printStackTrace();
            }
        }
        appointmentService.save(appointment);

        forward.getAttributes().put("message", "Прием у врача успешно сохранена");

        return forward;
    }
}
