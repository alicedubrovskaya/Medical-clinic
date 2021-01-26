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

public class LoginCommand extends Command {

    private static Map<Role, List<MenuItem>> menu = new ConcurrentHashMap<>();

    static {
        menu.put(Role.ADMINISTRATOR, new ArrayList<>(Arrays.asList(
                new MenuItem("/doctor/list.html", "Врачи", "Doctors", Arrays.asList(
                        new MenuItem("/user/edit.html", "Регистрация врача", "Doctor registration"),
                        new MenuItem("/doctor/list.html", "Список врачей", "Doctors list")
                )),
                new MenuItem("Пациенты", "Patients", Arrays.asList(
                        new MenuItem("/patient/list.html", "Список пациентов", "Patient list")
                )),
                new MenuItem("Пользователи", "Users", Arrays.asList(
                        new MenuItem("/user/list.html", "Список пользователей", "Users list")
                )),
                new MenuItem("Расписание", "Timetable", Arrays.asList(
                        new MenuItem("/appointment/generate.html", "Генерация расписания", "Timetable generation"),
                        new MenuItem("/appointment/list.html", "Приемы врачей", "Appointments")
                )),
                new MenuItem("Отпуски", "Vacations", Arrays.asList(
                        new MenuItem("/vacation/list.html", "Управление отпусками", "Vacations management"),
                        new MenuItem("/vacation/edit.html", "Добавить отпуск", "Add vacation")
                ))
        )));
        menu.put(Role.DOCTOR, new ArrayList<>(Arrays.asList(
                new MenuItem("/doctor/edit.html", "Мой профиль", "My profile"),
                new MenuItem("/appointment/doctor/choice.html", "Прием пациентов", "My appointments")
        )));

        menu.put(Role.PATIENT, new ArrayList<>(Arrays.asList(
                new MenuItem("/appointment/choice.html", "Записаться к врачу", "To make an appointment"),
                new MenuItem("/appointment/medicalCard.html", "История посещений врачей", "History of appointments"),
                new MenuItem("/patient/edit.html", "Мой профиль", "My profile")
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
                session.setAttribute("language", "en");
                //TODO
                return new Forward("/main.html");
            } else {
                request.setAttribute("message", "Логин пользователя или пароль не опознанны");
            }
        }
        return null;
    }
}
