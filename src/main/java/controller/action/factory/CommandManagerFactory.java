package controller.action.factory;

import controller.action.CommandManager;
import controller.action.CommandManagerImpl;
import service.ServiceFactory;

public class CommandManagerFactory {
    public static CommandManager getManager(ServiceFactory factory) {
        return new CommandManagerImpl(factory);
    }
}
