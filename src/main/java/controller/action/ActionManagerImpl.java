package controller.action;

import exception.PersistentException;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionManagerImpl implements ActionManager {
    private ServiceFactory serviceFactory;

    public ActionManagerImpl(ServiceFactory factory){this.serviceFactory=factory;}

    @Override
    public Action.Forward execute(Action action, HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        action.setFactory(serviceFactory);
        return action.exec(request, response);
    }

    @Override
    public void close() {
        serviceFactory.close();
    }
}
