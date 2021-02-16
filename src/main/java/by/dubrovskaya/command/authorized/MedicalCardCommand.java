package by.dubrovskaya.command.authorized;

import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.enumeration.ParameterType;
import by.dubrovskaya.domain.Appointment;
import by.dubrovskaya.domain.User;
import by.dubrovskaya.domain.enumeration.Role;
import by.dubrovskaya.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.dubrovskaya.service.AppointmentService;
import by.dubrovskaya.service.PatientService;
import by.dubrovskaya.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class MedicalCardCommand extends AuthorizedUserCommand {
    private static final Logger logger = LogManager.getLogger(MedicalCardCommand.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        AppointmentService appointmentService = serviceFactory.getAppointmentService();
        PatientService patientService = serviceFactory.getPatientService();
        String parameter = request.getParameter(ParameterType.ID.getValue());
        Integer id = null;
        if (parameter == null) {
            HttpSession session = request.getSession(false);
            User authorizedUser = (User) session.getAttribute(AttributeType.USER_AUTHORIZED.getValue());
            if (authorizedUser.getRole().equals(Role.PATIENT)) {
                id = authorizedUser.getId();
            }
        } else {
            id = Integer.parseInt(parameter);
        }

        if (id != null) {
            try {
                List<Appointment> appointments = appointmentService.findByPatient(id);
                List<String> diseases = patientService.findDiseasesByPatient(id);
                request.setAttribute(AttributeType.APPOINTMENTS.getValue(), appointments);
                request.setAttribute(AttributeType.DISEASES.getValue(), diseases);
            } catch (ServicePersistentException e) {
                logger.error(e);
            }
        }
        return null;
    }
}
