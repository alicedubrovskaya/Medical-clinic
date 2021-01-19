package validator;

import domain.Appointment;
import domain.Doctor;
import domain.enumeration.Shift;
import domain.enumeration.Status;
import exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class AppointmentValidator implements Validator<Appointment> {
    @Override
    public Appointment validate(HttpServletRequest request) throws IncorrectFormDataException {
        Appointment appointment = new Appointment();
        String parameter = request.getParameter("complaints");
        if (parameter != null) {
            try {
                appointment.setComplaints(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("complaints", parameter);
            }
        }

        parameter = request.getParameter("medicalReport");
        if (parameter != null) {
            try {
                appointment.setMedicalReport(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("medicalReport", parameter);
            }
        }

        parameter = request.getParameter("recommendation");
        if (parameter != null) {
            try {
                appointment.setRecommendation(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("recommendation", parameter);
            }
        }

        parameter = request.getParameter("status");
        if (parameter != null) {
            try {
                appointment.setStatus(Status.getEnum(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("status", parameter);
            }
        }


        return appointment;
    }
}
