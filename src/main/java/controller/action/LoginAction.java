package controller.action;

import domain.User;
import domain.enumeration.Role;
import exception.PersistentException;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LoginAction extends Action {

    private static Map<Role, List<MenuItem>> menu = new ConcurrentHashMap<>();

    static {
        menu.put(Role.ADMINISTRATOR, new ArrayList<>(Arrays.asList(
                new MenuItem("/doctor/list.html", "Врачи"),
                new MenuItem("/patient/list.html", "Пациенты"),
                new MenuItem("/appointment/generate.html", "Управление расписанием приемов врачей"),
                new MenuItem("/vacation/list.html", "Управление отпусками")
        )));
        menu.put(Role.DOCTOR, new ArrayList<>(Arrays.asList(
                new MenuItem("/doctor/edit.html", "Мой профиль"),
                new MenuItem("/appointment/doctor/choice.html", "Прием пациентов")
        )));

        menu.put(Role.PATIENT, new ArrayList<>(Arrays.asList(
                new MenuItem("/appointment/choice.html", "Записаться к врачу"),
                new MenuItem("/appointment/medicalCard.html", "История посещений врачей"),
                new MenuItem("/patient/edit.html", "Мой профиль")

        )));
    }

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }


    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login != null & password != null) {
            UserService service = serviceFactory.getUserService();
            User user = service.findByLoginAndPassword(login, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("authorizedUser", user);
                session.setAttribute("menu", menu.get(user.getRole()));

                //TODO
                return new Forward("/main.html");
            } else {
                request.setAttribute("message", "Логин пользователя или пароль не опознанны");
            }
        }
        return null;
    }
}
