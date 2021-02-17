package by.dubrovskaya.command.admin;

import by.dubrovskaya.domain.Vacation;
import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.exception.IncorrectFormDataException;
import by.dubrovskaya.exception.ServicePersistentException;
import by.dubrovskaya.service.VacationService;
import by.dubrovskaya.service.validator.PaginationValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class VacationListCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(VacationListCommand.class);
    private static final int RECORDS_PER_PAGE = 1;
    private int page = 1;

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        PaginationValidator validator = validatorFactory.createPaginationValidator();

        VacationService service = serviceFactory.getVacationService();
        try {
            Integer currentPage = validator.validatePage(request);
            if (currentPage != null) {
                page = validator.validatePage(request);
            }
            Map<Integer, List<Vacation>> map = service.find((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
            int noOfRecords = 1;
            for (Integer key : map.keySet()) {
                noOfRecords = key;
            }
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
            request.setAttribute(AttributeType.VACATIONS.getValue(), map.get(noOfRecords));
            request.setAttribute(AttributeType.NUMBER_OF_PAGES.getValue(), noOfPages);
            request.setAttribute(AttributeType.CURRENT_PAGE.getValue(), page);
        } catch (ServicePersistentException | IncorrectFormDataException e) {
            logger.error(e);
        }
        return null;
    }
}
