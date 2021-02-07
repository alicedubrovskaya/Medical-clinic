package controller.action.admin;

import domain.User;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class UserListCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(UserListCommand.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        int page = 1;
        int recordsPerPage = 3;
        String parameter = request.getParameter("page");
        if (parameter != null) {
            try {
                page = Integer.parseInt(parameter);
                if (page <= 0) {
                    return null;
                }
            } catch (NumberFormatException e) {
                return null;
            }
        }

        try {
            UserService service = serviceFactory.getUserService();
            Map<Integer, List<User>> map = service.find((page - 1) * recordsPerPage, recordsPerPage);
            int noOfRecords = 1;
            for (Integer key : map.keySet()) {
                noOfRecords = key;
            }
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            request.setAttribute("users", map.get(noOfRecords));
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
        } catch (ServicePersistentException e) {
            logger.error("Service can not perform operation with retrieving users");
        }
        return null;
    }
}
