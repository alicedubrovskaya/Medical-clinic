package validator;

import domain.Doctor;
import domain.enumeration.Shift;
import exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class DoctorValidator implements Validator<Doctor> {
    @Override
    public Doctor validate(HttpServletRequest request) throws IncorrectFormDataException {
        Doctor doctor = new Doctor();
        String parameter = request.getParameter("id");
        if (parameter != null) {
            try {
                doctor.setId(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("id", parameter);
            }
        }

        parameter = request.getParameter("name");
        if (parameter != null) {
            try {
                doctor.setName(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("name", parameter);
            }
        }

        parameter = request.getParameter("surname");
        if (parameter != null) {
            try {
                doctor.setSurname(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("surname", parameter);
            }
        }

        parameter = request.getParameter("specialization");
        if (parameter != null) {
            try {
                doctor.setSpecialization(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("specialization", parameter);
            }
        }

        parameter = request.getParameter("workingShift");
        if (parameter != null) {
            try {
                doctor.setWorkingShift(Shift.getEnum(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("workingShift", parameter);
            }
        }

        return doctor;
    }
}
