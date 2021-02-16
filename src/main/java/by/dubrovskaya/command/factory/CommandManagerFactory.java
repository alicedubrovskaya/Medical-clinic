package by.dubrovskaya.command.factory;

import by.dubrovskaya.command.CommandManager;
import by.dubrovskaya.command.CommandManagerImpl;
import by.dubrovskaya.service.ServiceFactory;

public class CommandManagerFactory {
    public static CommandManager getManager(ServiceFactory factory) {
        return new CommandManagerImpl(factory);
    }
}
