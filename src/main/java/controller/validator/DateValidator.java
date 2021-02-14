package controller.validator;

import controller.enumeration.ParameterType;
import exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class DateValidator {
    public Date validateDate(HttpServletRequest request) throws IncorrectFormDataException {
        String parameter = request.getParameter(ParameterType.DATE.getValue());
        Date calendar = null;
        if (parameter != null) {
            try {
                calendar = java.sql.Date.valueOf(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.DATE.getValue(), parameter);
            }
        }
        return calendar;
    }

    public int validateDays(HttpServletRequest request) throws IncorrectFormDataException {
        String countOfDays = request.getParameter(ParameterType.DAYS.getValue());
        int days = 0;
        if (countOfDays != null) {
            try {
                days = Integer.parseInt(countOfDays);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.DAYS.getValue(), countOfDays);
            }
        }
        return days;
    }
}
