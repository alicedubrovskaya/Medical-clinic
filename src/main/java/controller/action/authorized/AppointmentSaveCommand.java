package controller.action.authorized;

import domain.Appointment;
import domain.Patient;
import domain.User;
import domain.enumeration.Status;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AppointmentService;
import service.PatientService;
import service.exception.ServicePersistentException;
import validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AppointmentSaveCommand extends AuthorizedUserCommand {
    private static final Logger logger = LogManager.getLogger(AppointmentSaveCommand.class);

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

        try {
            String complaints = request.getParameter("complaints");
            String disease = null;
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

                    disease = request.getParameter("diseases");
                } catch (IncorrectFormDataException e) {
                    e.printStackTrace();
                }
            }

            if (disease == null) {
                appointmentService.save(appointment);
            } else {
                appointmentService.save(appointment, disease);
            }
            forward.getAttributes().put("message", "Прием у врача успешно сохранен");
        } catch (ServicePersistentException e) {
            logger.error(e);
        }
        return forward;
    }
}
