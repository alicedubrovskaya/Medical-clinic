package controller.action;

import domain.enumeration.Role;
import exception.PersistentException;
import service.ServiceFactory;
import validator.ValidatorFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

abstract public class Command {
    private Set<Role> allowRoles = new HashSet<>();
    private String name;

    protected ServiceFactory serviceFactory;
    protected ValidatorFactory validatorFactory;

    public Command() {
    }

    public String getName() {
        return name;
    }

    public Set<Role> getAllowRoles() {
        return allowRoles;
    }

    public void setFactory(ServiceFactory factory) {
        this.serviceFactory = factory;
        this.validatorFactory = ValidatorFactory.getINSTANCE();
    }

    public void setName(String name) {
        this.name = name;
    }

    abstract public Command.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException;

    public static class Forward {
        private String forward;
        private boolean redirect;
        private Map<String, Object> attributes = new HashMap<>();

        public Forward(String forward, boolean redirect) {
            this.forward = forward;
            this.redirect = redirect;
        }

        public Forward(String forward) {
            this(forward, true);
        }

        public String getForward() {
            return forward;
        }


        public void setForward(String forward) {
            this.forward = forward;
        }

        public boolean isRedirect() {
            return redirect;
        }

        public void setRedirect(boolean redirect) {
            this.redirect = redirect;
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }
    }
}
