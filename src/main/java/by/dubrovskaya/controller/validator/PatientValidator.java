package by.dubrovskaya.controller.validator;

import by.dubrovskaya.domain.enumeration.ParameterType;
import by.dubrovskaya.domain.Patient;
import by.dubrovskaya.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class PatientValidator implements Validator<Patient> {
    @Override
    public Patient validate(HttpServletRequest request) throws IncorrectFormDataException {
        Patient patient = new Patient();
        String parameter = request.getParameter(ParameterType.ID.getValue());
        if (parameter != null) {
            try {
                patient.setId(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.ID.getValue(), parameter);
            }
        }

        parameter = request.getParameter(ParameterType.NAME.getValue());
        if (parameter != null) {
            try {
                patient.setName(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.NAME.getValue(), parameter);
            }
        }

        parameter = request.getParameter(ParameterType.SURNAME.getValue());
        if (parameter != null) {
            try {
                patient.setSurname(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.SURNAME.getValue(), parameter);
            }
        }

        parameter = request.getParameter(ParameterType.EMAIL.getValue());
        if (parameter != null) {
            try {
                patient.setEmail(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.EMAIL.getValue(), parameter);
            }
        }

        parameter = request.getParameter(ParameterType.PHONE_NUMBER.getValue());
        if (parameter != null) {
            try {
                patient.setPhoneNumber(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.PHONE_NUMBER.getValue(), parameter);
            }
        }

        parameter = request.getParameter(ParameterType.ADDRESS.getValue());
        if (parameter != null) {
            try {
                patient.setAddress(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.ADDRESS.getValue(), parameter);
            }
        }
        return patient;
    }
}
