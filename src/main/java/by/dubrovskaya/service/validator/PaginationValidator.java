package by.dubrovskaya.service.validator;

import by.dubrovskaya.domain.enumeration.ParameterType;
import by.dubrovskaya.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class PaginationValidator {

    public Integer validatePage(HttpServletRequest request) throws IncorrectFormDataException {
        Integer currentPage = null;
        String parameter = request.getParameter(ParameterType.PAGE.getValue());
        if (parameter != null) {
            try {
                currentPage = Integer.parseInt(parameter);
                if (currentPage <= 0) {
                    return null;
                }
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterType.PAGE.getValue(), parameter);
            }
        }
        return currentPage;
    }

}
