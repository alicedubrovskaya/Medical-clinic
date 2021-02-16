package by.dubrovskaya.controller.validator;

import by.dubrovskaya.domain.enumeration.ParameterType;
import by.dubrovskaya.domain.Vacation;
import by.dubrovskaya.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class VacationValidator implements Validator<Vacation> {
    @Override
    public Vacation validate(HttpServletRequest request) throws IncorrectFormDataException {
        Vacation vacation = new Vacation();
        String parameter = request.getParameter(ParameterType.ID.getValue());
        if (parameter != null) {
            try {
                vacation.setId(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("id", parameter);
            }
        }

        parameter = request.getParameter(ParameterType.VACATION_START.getValue());
        if (parameter != null) {
            try {
                vacation.setStart(java.sql.Date.valueOf(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.VACATION_START.getValue(), parameter);
            }
        }

        parameter = request.getParameter(ParameterType.VACATION_END.getValue());
        if (parameter != null) {
            try {
                vacation.setEnd(java.sql.Date.valueOf(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.VACATION_END.getValue(), parameter);
            }
        }
        return vacation;
    }
}
