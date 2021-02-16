package by.dubrovskaya.command.admin;

import by.dubrovskaya.command.Command;
import by.dubrovskaya.domain.enumeration.AttributeType;
import by.dubrovskaya.domain.enumeration.CommandType;
import by.dubrovskaya.domain.enumeration.ParameterType;
import by.dubrovskaya.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.dubrovskaya.service.util.ResourceBundleUtil;
import by.dubrovskaya.service.UserService;
import by.dubrovskaya.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

public class UserDeleteCommand extends AdministratorCommand {
    private static final Logger logger = LogManager.getLogger(UserDeleteCommand.class);
    private static final String HTML = ".html";
    private static final String SUCCESSFUL_DELETING = "message.user.deleted";

    @Override
    public Command.Forward exec(HttpServletRequest request, HttpServletResponse response) {
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
        Command.Forward forward = new Command.Forward(CommandType.USER_LIST.getCommand() + HTML);
        try {
            UserService service = serviceFactory.getUserService();
            Integer id = Integer.parseInt(request.getParameter(ParameterType.ID.getValue()));
            service.delete(id);
            forward.getAttributes().put(AttributeType.MESSAGE.getValue(), rb.getString(SUCCESSFUL_DELETING));
        } catch (NumberFormatException | ServicePersistentException e) {
            logger.error(e);
        }
        return forward;
    }
}
