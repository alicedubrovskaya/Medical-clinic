package controller.action;

import exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionManager {
    Command.Forward execute(Command command, HttpServletRequest request, HttpServletResponse response) throws PersistentException;

    void close();
}
