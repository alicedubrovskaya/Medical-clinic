package by.dubrovskaya.command.authorized;

import by.dubrovskaya.controller.validator.Validator;
import by.dubrovskaya.domain.Appointment;
import by.dubrovskaya.domain.Patient;
import by.dubrovskaya.domain.User;
import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.enumeration.CommandType;
import by.dubrovskaya.domain.enumeration.ParameterType;
import by.dubrovskaya.exception.IncorrectFormDataException;
import by.dubrovskaya.exception.ServicePersistentException;
import by.dubrovskaya.service.AppointmentService;
import by.dubrovskaya.service.PatientService;
import by.dubrovskaya.service.util.ResourceBundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

public class AppointmentSaveCommand extends AuthorizedUserCommand {
    private static final Logger logger = LogManager.getLogger(AppointmentSaveCommand.class);
    private static final String HTML = ".html";
    private static final String SUCCESSFUL_SAVING = "message.appointment.saved";

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
        Forward forward = new Forward(CommandType.APPOINTMENT_LIST.getCommand() + HTML);
        HttpSession session = request.getSession(false);
        User authorizedUser = (User) session.getAttribute(AttributeType.USER_AUTHORIZED.getValue());

        Integer patientId = (Integer) request.getAttribute(AttributeType.PATIENT_ID.getValue());
        if (patientId == null) {
            String parameter = request.getParameter(ParameterType.PATIENT_ID.getValue());
            if (parameter != null) {
                patientId = Integer.parseInt(parameter);
            }
        }

        if (patientId == null) {
            patientId = authorizedUser.getId();
        }

        Integer appointmentId = (Integer) request.getAttribute(AttributeType.APPOINTMENT_ID.getValue());
        if (appointmentId == null) {
            appointmentId = Integer.parseInt(request.getParameter(ParameterType.APPOINTMENT_ID.getValue()));
        }

        AppointmentService appointmentService = serviceFactory.getAppointmentService();
        try {
            Appointment appointment = appointmentService.findById(appointmentId);

            String complaints = request.getParameter(ParameterType.COMPLAINTS.getValue());
            String disease = null;
            if (complaints == null) {
                PatientService patientService = serviceFactory.getPatientService();
                Patient patient = patientService.findById(patientId);
                appointment.setPatient(patient);
            } else {
                Validator<Appointment> validator = validatorFactory.createAppointmentValidator();
                Appointment appointmentFromRequest = validator.validate(request);
                appointment.setComplaints(appointmentFromRequest.getComplaints());
                appointment.setStatus(appointmentFromRequest.getStatus());
                appointment.setRecommendation(appointmentFromRequest.getRecommendation());
                appointment.setMedicalReport(appointmentFromRequest.getMedicalReport());

                disease = request.getParameter(ParameterType.DISEASES.getValue());
            }

            if (disease == null) {
                appointmentService.save(appointment);
            } else {
                appointmentService.save(appointment, disease);
            }
            forward.getAttributes().put(AttributeType.MESSAGE.getValue(), rb.getString(SUCCESSFUL_SAVING));
        } catch (ServicePersistentException | IncorrectFormDataException e) {
            logger.error(e);
        }
        return forward;
    }
}
