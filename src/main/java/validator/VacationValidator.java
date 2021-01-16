package validator;

import domain.Vacation;
import domain.enumeration.Shift;
import exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class VacationValidator implements Validator<Vacation> {
    @Override
    public Vacation validate(HttpServletRequest request) throws IncorrectFormDataException {
        Vacation vacation = new Vacation();
        String parameter = request.getParameter("id");
        if (parameter != null) {
            try {
                vacation.setId(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("id", parameter);
            }
        }

        parameter = request.getParameter("vacation-start");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd");
        if (parameter != null) {
            try {
                vacation.setStart(java.sql.Date.valueOf(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("vacation-start", parameter);
            }
        }

        parameter = request.getParameter("vacation-end");
        if (parameter != null) {
            try {
                vacation.setEnd(java.sql.Date.valueOf(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("vacation-start", parameter);
            }
        }

        return vacation;
    }
}
