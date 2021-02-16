package by.dubrovskaya.command;

import by.dubrovskaya.exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandManager {
    Command.Forward execute(Command command, HttpServletRequest request, HttpServletResponse response) throws PersistentException;

    void close();
}
