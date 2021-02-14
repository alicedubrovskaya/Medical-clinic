package controller;

import controller.action.Command;
import controller.action.CommandManager;
import controller.action.factory.CommandManagerFactory;
import controller.enumeration.AttributeType;
import dao.database.TransactionFactoryImpl;
import dao.pool.ConnectionPool;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.impl.ServiceFactoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(DispatcherServlet.class);
    private static final String JSP = ".jsp";

    public ServiceFactory getFactory() throws PersistentException {
        return new ServiceFactoryImpl(new TransactionFactoryImpl());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Command command = (Command) request.getAttribute(AttributeType.ACTION.getValue());
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                @SuppressWarnings("unchecked")
                Map<String, Object> attributes = (Map<String, Object>) session.getAttribute(AttributeType.REDIRECTED_DATA.getValue());
                if (attributes != null) {
                    for (String key : attributes.keySet()) {
                        request.setAttribute(key, attributes.get(key));
                    }
                    session.removeAttribute(AttributeType.REDIRECTED_DATA.getValue());
                }
            }

            CommandManager commandManager = CommandManagerFactory.getManager(getFactory());
            Command.Forward forward = commandManager.execute(command, request, response);
            commandManager.close();

            if (session != null && forward != null && !forward.getAttributes().isEmpty()) {
                session.setAttribute(AttributeType.REDIRECTED_DATA.getValue(), forward.getAttributes());
            }
            String requestedUri = request.getRequestURI();
            if (forward != null && forward.isRedirect()) {
                String redirectedUri = request.getContextPath() + forward.getForward();
                logger.debug("Request for URI {} id redirected to URI {}", requestedUri, redirectedUri);
                response.sendRedirect(redirectedUri);
            } else {
                String jspPage;
                if (forward != null) {
                    jspPage = forward.getForward();
                } else {
                    jspPage = command.getName() + JSP;
                }
                jspPage = "/WEB-INF/jsp"+ jspPage;
                logger.debug("Request for URI {}} is forwarded to JSP {}", requestedUri, jspPage);
                getServletContext().getRequestDispatcher(jspPage).forward(request, response);
            }

        } catch (PersistentException e) {
            logger.error("It is impossible to process request", e);
            request.setAttribute(AttributeType.ERROR.getValue(), "Error");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroy();
    }
}
