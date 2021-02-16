package by.dubrovskaya.controller.validator;

import by.dubrovskaya.domain.enumeration.ParameterType;
import by.dubrovskaya.domain.Appointment;
import by.dubrovskaya.domain.enumeration.Status;
import by.dubrovskaya.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class AppointmentValidator implements Validator<Appointment> {
    @Override
    public Appointment validate(HttpServletRequest request) throws IncorrectFormDataException {
        Appointment appointment = new Appointment();
        String parameter = request.getParameter(ParameterType.COMPLAINTS.getValue());
        if (parameter != null) {
            try {
                appointment.setComplaints(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.COMPLAINTS.getValue(), parameter);
            }
        }

        parameter = request.getParameter(ParameterType.MEDICAL_REPORT.getValue());
        if (parameter != null) {
            try {
                appointment.setMedicalReport(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.MEDICAL_REPORT.getValue(), parameter);
            }
        }

        parameter = request.getParameter(ParameterType.RECOMMENDATION.getValue());
        if (parameter != null) {
            try {
                appointment.setRecommendation(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.RECOMMENDATION.getValue(), parameter);
            }
        }

        parameter = request.getParameter(ParameterType.STATUS.getValue());
        if (parameter != null) {
            try {
                appointment.setStatus(Status.getEnum(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.STATUS.getValue(), parameter);
            }
        }

        return appointment;
    }
}
