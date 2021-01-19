package controller;

import controller.action.Action;
import controller.action.authorized.MainAction;
import domain.User;
import domain.enumeration.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            Action action = (Action) httpRequest.getAttribute("action");
            Set<Role> allowRoles = action.getAllowRoles();
            String userName = "unauthorized user";
            HttpSession session = httpRequest.getSession(false);
            User user = null;
            if (session != null) {
                user = (User) session.getAttribute("authorizedUser");
                action.setAuthorizedUser(user);
                String errorMessage = (String) session.getAttribute("SecurityFilterMessage");
                if (errorMessage != null) {
                    httpRequest.setAttribute("message", errorMessage);
                    session.removeAttribute("SecurityFilterMessage");
                }
            }
            boolean canExecute = allowRoles == null;
            if (user != null) {
                userName = "\"" + user.getLogin() + "\" user";
                canExecute = canExecute || allowRoles.contains(user.getRole());
            }
            if (canExecute) {
                chain.doFilter(request, response);
            } else {
                if (session != null && action.getClass() != MainAction.class) {
                    session.setAttribute("SecurityFilterMessage", "Доступ запрещён");
                }
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.html");
            }
        } else {
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}