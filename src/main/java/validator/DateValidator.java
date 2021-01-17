package validator;

import exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateValidator {
    public Date validateDate(HttpServletRequest request) throws IncorrectFormDataException {
        String parameter = request.getParameter("start");
        Date calendar = new GregorianCalendar(2021, 01, 01).getTime();
//        Calendar calendar = new GregorianCalendar(2021, 01, 1);
        if (parameter != null) {
            try {
//                int day = Integer.parseInt(date.substring(0, 4));
//                int month = Integer.parseInt(date.substring(5, 7));
//                int year = Integer.parseInt(date.substring(8, 10));
//                calendar = new GregorianCalendar(year, month, day);
                calendar = java.sql.Date.valueOf(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("date", parameter);
            }
        }
        return calendar;
    }

    public int validateDays(HttpServletRequest request) throws IncorrectFormDataException {
        String countOfDays = request.getParameter("days");
        int days = 0;
        if (countOfDays != null) {
            try {
                days = Integer.parseInt(countOfDays);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("days", countOfDays);
            }
        }
        return days;
    }
}
