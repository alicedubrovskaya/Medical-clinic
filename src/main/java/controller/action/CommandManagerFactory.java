package controller.action;

import service.ServiceFactory;

public class CommandManagerFactory {
    public static CommandManager getManager(ServiceFactory factory) {
        return new CommandManagerImpl(factory);
    }
}
