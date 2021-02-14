package controller.action.doctor;

import controller.enumeration.AttributeType;
import controller.enumeration.ParameterType;
import domain.Appointment;
import domain.enumeration.Status;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AppointmentService;
import service.PatientService;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class AppointmentEditCommand extends DoctorCommand {
    private static final Logger logger = LogManager.getLogger(AppointmentEditCommand.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {

        AppointmentService service = serviceFactory.getAppointmentService();
        PatientService patientService = serviceFactory.getPatientService();

        try {
            Integer id = (Integer) request.getAttribute(AttributeType.APPOINTMENT_ID.getValue());
            if (id == null) {
                id = Integer.parseInt(request.getParameter(ParameterType.APPOINTMENT_ID.getValue()));
            }

            Appointment appointment = service.findById(id);
            if (appointment != null) {
                request.setAttribute(AttributeType.APPOINTMENT.getValue(), appointment);
                request.setAttribute(AttributeType.STATUSES.getValue(), Arrays.asList(Status.WAS.getName(), Status.MISSED.getName()));
            }
        } catch (NumberFormatException e) {
            logger.error(e);
        }

        try {
            List<String> diseases = patientService.findDiseases();
            request.setAttribute(AttributeType.DISEASES.getValue(), diseases);
        } catch (ServicePersistentException e) {
            logger.error(e);
        }
        return null;
    }
}
