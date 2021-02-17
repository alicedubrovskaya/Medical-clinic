package by.dubrovskaya.service.validator;

import by.dubrovskaya.domain.Entity;
import by.dubrovskaya.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public interface Validator <Type extends Entity> {
    Type validate(HttpServletRequest request) throws IncorrectFormDataException;
}
