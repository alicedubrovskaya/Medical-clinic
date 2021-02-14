package controller.action.authorized;

import controller.enumeration.AttributeType;
import controller.enumeration.CommandType;
import controller.enumeration.ParameterType;
import controller.validator.Validator;
import domain.Appointment;
import domain.Patient;
import domain.User;
import exception.IncorrectFormDataException;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AppointmentService;
import service.PatientService;
import service.ResourceBundleUtil;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

public class AppointmentSaveCommand extends AuthorizedUserCommand {
    private static final Logger logger = LogManager.getLogger(AppointmentSaveCommand.class);
    private static final String HTML = ".html";
    private static final String SUCCESSFUL_SAVING = "message.appointment.saved";

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
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
        Appointment appointment = appointmentService.findById(appointmentId);

        try {
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
