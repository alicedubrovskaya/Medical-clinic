package by.dubrovskaya.command.admin;

import by.dubrovskaya.command.Command;
import by.dubrovskaya.domain.User;
import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.Patient;
import by.dubrovskaya.domain.enumeration.ParameterType;
import by.dubrovskaya.exception.IncorrectFormDataException;
import by.dubrovskaya.exception.PersistentException;
import by.dubrovskaya.service.UserService;
import by.dubrovskaya.service.validator.PaginationValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.dubrovskaya.service.PatientService;
import by.dubrovskaya.service.util.ResourceBundleUtil;
import by.dubrovskaya.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class PatientListCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(PatientListCommand.class);
    private static final String PATIENTS_NOT_FOUND = "message.patient.notFound";
    private static final int RECORDS_PER_PAGE = 1;
    private int page = 1;

    @Override
    public Command.Forward exec(HttpServletRequest request, HttpServletResponse response) {
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
        PaginationValidator validator = validatorFactory.createPaginationValidator();

        try {
            Integer currentPage = validator.validatePage(request);
            if (currentPage != null) {
                page = validator.validatePage(request);
            }

            PatientService service = serviceFactory.getPatientService();
            Map<Integer, List<Patient>> map = service.find((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
            int noOfRecords = 1;
            for (Integer key : map.keySet()) {
                noOfRecords = key;
            }
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
            request.setAttribute(AttributeType.PATIENTS.getValue(), map.get(noOfRecords));
            request.setAttribute(AttributeType.NUMBER_OF_PAGES.getValue(), noOfPages);
            request.setAttribute(AttributeType.CURRENT_PAGE.getValue(), page);
        } catch (ServicePersistentException | IncorrectFormDataException e) {
            logger.error(e);
            request.setAttribute(AttributeType.MESSAGE.getValue(), rb.getString(PATIENTS_NOT_FOUND));
        }
        return null;
    }
}
