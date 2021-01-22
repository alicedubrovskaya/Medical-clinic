package controller.action;

import exception.PersistentException;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommandManagerImpl implements CommandManager {
    private final ServiceFactory serviceFactory;

    public CommandManagerImpl(ServiceFactory factory){this.serviceFactory=factory;}

    @Override
    public Command.Forward execute(Command command, HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        command.setFactory(serviceFactory);
        return command.exec(request, response);
    }

    @Override
    public void close() {
        serviceFactory.close();
    }
}
