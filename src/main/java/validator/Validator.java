package validator;

import domain.Entity;
import exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public interface Validator <Type extends Entity> {
    Type validate(HttpServletRequest request) throws IncorrectFormDataException;
}
