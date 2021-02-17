package by.dubrovskaya.service.validator;

import by.dubrovskaya.domain.enumeration.ParameterType;
import by.dubrovskaya.domain.Doctor;
import by.dubrovskaya.domain.enumeration.Shift;
import by.dubrovskaya.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class DoctorValidator implements Validator<Doctor> {
    @Override
    public Doctor validate(HttpServletRequest request) throws IncorrectFormDataException {
        Doctor doctor = new Doctor();
        String parameter = request.getParameter(ParameterType.ID.getValue());
        if (parameter != null) {
            try {
                doctor.setId(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.ID.getValue(), parameter);
            }
        }

        parameter = request.getParameter(ParameterType.NAME.getValue());
        if (parameter != null) {
            try {
                doctor.setName(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.NAME.getValue(), parameter);
            }
        }

        parameter = request.getParameter(ParameterType.SURNAME.getValue());
        if (parameter != null) {
            try {
                doctor.setSurname(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.SURNAME.getValue(), parameter);
            }
        }

        parameter = request.getParameter(ParameterType.SPECIALIZATION.getValue());
        if (parameter != null) {
            try {
                doctor.setSpecialization(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.SPECIALIZATION.getValue(), parameter);
            }
        }

        parameter = request.getParameter(ParameterType.WORKING_SHIFT.getValue());
        if (parameter != null) {
            try {
                doctor.setWorkingShift(Shift.getEnum(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.WORKING_SHIFT.getValue(), parameter);
            }
        }

        return doctor;
    }
}
