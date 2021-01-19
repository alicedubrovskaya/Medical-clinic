package controller;

import controller.action.Action;
import controller.action.LoginAction;
import controller.action.admin.appointment.GenerateAppointmentsAction;
import controller.action.admin.doctor.DoctorDeleteAction;
import controller.action.admin.doctor.DoctorListAction;
import controller.action.admin.doctor.DoctorSaveAction;
import controller.action.admin.patient.MedicalCardAction;
import controller.action.admin.patient.PatientListAction;
import controller.action.admin.patient.PatientSaveAction;
import controller.action.admin.user.UserDeleteAction;
import controller.action.admin.user.UserEditAction;
import controller.action.admin.user.UserListAction;
import controller.action.admin.user.UserSaveAction;
import controller.action.admin.vacation.VacationDeleteAction;
import controller.action.admin.vacation.VacationEditAction;
import controller.action.admin.vacation.VacationListAction;
import controller.action.admin.vacation.VacationSaveAction;
import controller.action.authorized.*;
import controller.action.doctor.AppointmentsChoiceAction;
import controller.action.patient.AppointmentChoiceAction;
import controller.action.patient.PatientEditAction;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActionFromUriFilter implements Filter {

    private static Map<String, Class<? extends Action>> actions = new ConcurrentHashMap<>();

    static {
        actions.put("/main", MainAction.class);
        actions.put("/login", LoginAction.class);

        actions.put("/doctor/save", DoctorSaveAction.class);
        actions.put("/doctor/list", DoctorListAction.class);
        actions.put("/doctor/edit", DoctorEditAction.class);
        actions.put("/doctor/delete", DoctorDeleteAction.class);

        actions.put("/user/save", UserSaveAction.class);
        actions.put("/user/edit", UserEditAction.class);
        actions.put("/user/list", UserListAction.class);
        actions.put("/user/delete", UserDeleteAction.class);

        actions.put("/vacation/save", VacationSaveAction.class);
        actions.put("/vacation/list", VacationListAction.class);
        actions.put("/vacation/edit", VacationEditAction.class);
        actions.put("/vacation/delete", VacationDeleteAction.class);

        actions.put("/patient/save", PatientSaveAction.class);
        actions.put("/patient/list", PatientListAction.class);
        actions.put("/patient/edit", PatientEditAction.class);

        actions.put("/appointment/save", AppointmentSaveAction.class);
        actions.put("/appointment/info", AppointmentInfoAction.class);
        actions.put("/appointment/list", AppointmentListAction.class);
        actions.put("/appointment/generate", GenerateAppointmentsAction.class);
        actions.put("/appointment/choice", AppointmentChoiceAction.class);
        actions.put("/appointment/doctor/choice", AppointmentsChoiceAction.class);
        actions.put("/appointment/medicalCard", MedicalCardAction.class);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String contextPath = httpRequest.getContextPath();
            String uri = httpRequest.getRequestURI();

            int beginAction = contextPath.length();
            int endAction = uri.lastIndexOf('.');
            String actionName;
            if (endAction >= 0) {
                actionName = uri.substring(beginAction, endAction);
            } else {
                actionName = uri.substring(beginAction);
            }
            Class<? extends Action> actionClass = actions.get(actionName);
            try {
                //TODO
                Action action = actionClass.newInstance();
                action.setName(actionName);
                httpRequest.setAttribute("action", action);
                chain.doFilter(request, response);
            } catch (InstantiationException | IllegalAccessException | NullPointerException e) {
                httpRequest.setAttribute("error", String.format("Запрошенный адрес %s не может быть обработан сервером", uri));
                httpRequest.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            }
        } else {
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
