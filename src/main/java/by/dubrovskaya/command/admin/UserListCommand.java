package by.dubrovskaya.command.admin;

import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.User;
import by.dubrovskaya.exception.IncorrectFormDataException;
import by.dubrovskaya.service.util.ResourceBundleUtil;
import by.dubrovskaya.service.validator.PaginationValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.dubrovskaya.service.UserService;
import by.dubrovskaya.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class UserListCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(UserListCommand.class);
    private static final String USERS_NOT_FOUND = "message.user.notFound";
    private static final int RECORDS_PER_PAGE = 1;
    private int page = 1;

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
        PaginationValidator validator = validatorFactory.createPaginationValidator();

        try {
            Integer currentPage = validator.validatePage(request);
            if (currentPage != null) {
                page = validator.validatePage(request);
            }
            UserService service = serviceFactory.getUserService();
            Map<Integer, List<User>> map = service.find((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
            int noOfRecords = 1;
            for (Integer key : map.keySet()) {
                noOfRecords = key;
            }
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
            request.setAttribute(AttributeType.USERS.getValue(), map.get(noOfRecords));
            request.setAttribute(AttributeType.NUMBER_OF_PAGES.getValue(), noOfPages);
            request.setAttribute(AttributeType.CURRENT_PAGE.getValue(), page);
        } catch (ServicePersistentException | IncorrectFormDataException e) {
            logger.error(e);
            request.setAttribute(AttributeType.MESSAGE.getValue(), rb.getString(USERS_NOT_FOUND));
        }
        return null;
    }
}
