package controller.action;

import exception.PersistentException;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionManagerImpl implements ActionManager {
    private ServiceFactory serviceFactory;

    public ActionManagerImpl(ServiceFactory factory){this.serviceFactory=factory;}

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
