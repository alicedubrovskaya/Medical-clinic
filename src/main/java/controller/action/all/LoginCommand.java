package controller.action.all;

import controller.action.Command;
import controller.enumeration.AttributeType;
import controller.enumeration.CommandType;
import controller.enumeration.ParameterType;
import domain.User;
import domain.enumeration.Role;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ResourceBundleUtil;
import service.UserService;
import service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LoginCommand extends Command {

    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private static final Map<Role, List<MenuItem>> menu = new ConcurrentHashMap<>();
    private static final String HTML = ".html";
    private static final String USER_UNRECOGNIZED = "user.unrecognized";

    static {
        menu.put(Role.ADMINISTRATOR, new ArrayList<>(Arrays.asList(
                new MenuItem("/doctor/list.html", "Врачи", "Doctors", Arrays.asList(
                        new MenuItem("/user/edit.html?registration=register", "Регистрация врача", "Doctor registration"),
                        new MenuItem("/doctor/list.html", "Список врачей", "Doctors list")
                )),
                new MenuItem("/patient/list.html", "Пациенты", "Patients"),
                new MenuItem("/user/list.html", "Пользователи", "Users"),
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
                new MenuItem("/appointment/list.html", "Прием пациентов", "Patient appointments"),
                new MenuItem("Личная информация", "Personal information", Arrays.asList(
                        new MenuItem("/doctor/edit.html", "Мой профиль", "My profile"),
                        new MenuItem("/user/edit.html", "Учетная запись", "Account")
                ))
        )));

        menu.put(Role.PATIENT, new ArrayList<>(Arrays.asList(
                new MenuItem("/appointment/choice.html", "Записаться к врачу", "To make an appointment"),
                new MenuItem("/appointment/medicalCard.html", "История посещений врачей", "History of appointments"),
                new MenuItem("Личная информация", "Personal information", Arrays.asList(
                        new MenuItem("/patient/edit.html", "Мой профиль", "My profile"),
                        new MenuItem("/user/edit.html", "Учетная запись", "Account")
                ))
        )));
    }

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        String login = request.getParameter(ParameterType.LOGIN.getValue());
        String password = request.getParameter(ParameterType.PASSWORD.getValue());
        if (login != null & password != null) {
            UserService service = serviceFactory.getUserService();
            try {
                User user = service.findByLoginAndPassword(login, password);
                HttpSession session = request.getSession();
                session.setAttribute(AttributeType.USER_AUTHORIZED.getValue(), user);
                session.setAttribute(AttributeType.MENU.getValue(), menu.get(user.getRole()));
                session.setAttribute(AttributeType.LANGUAGE.getValue(), "en");
                return new Forward(CommandType.MAIN.getCommand() + HTML);
            } catch (ServicePersistentException e) {
                logger.error(e);
                request.setAttribute(AttributeType.MESSAGE.getValue(), rb.getString(USER_UNRECOGNIZED));
            }
        }
        return null;
    }
}
