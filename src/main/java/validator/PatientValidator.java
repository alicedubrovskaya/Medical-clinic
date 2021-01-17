package validator;

import domain.Patient;
import exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class PatientValidator implements Validator<Patient> {
    @Override
    public Patient validate(HttpServletRequest request) throws IncorrectFormDataException {
        Patient patient = new Patient();
        String parameter = request.getParameter("id");
        if (parameter != null) {
            try {
                patient.setId(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("id", parameter);
            }
        }

        parameter = request.getParameter("name");
        if (parameter != null) {
            try {
                patient.setName(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("name", parameter);
            }
        }

        parameter = request.getParameter("surname");
        if (parameter != null) {
            try {
                patient.setSurname(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("surname", parameter);
            }
        }

        parameter = request.getParameter("email");
        if (parameter != null) {
            try {
                patient.setEmail(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("email", parameter);
            }
        }

        parameter = request.getParameter("phoneNumber");
        if (parameter != null) {
            try {
                patient.setPhoneNumber(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("phoneNumber", parameter);
            }
        }

        parameter = request.getParameter("address");
        if (parameter != null) {
            try {
                patient.setAddress(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("address", parameter);
            }
        }
        return patient;
    }
}
