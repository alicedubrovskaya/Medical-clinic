package by.dubrovskaya.command.factory;

import by.dubrovskaya.command.Command;
import by.dubrovskaya.command.admin.*;
import by.dubrovskaya.command.authorized.*;
import by.dubrovskaya.command.doctor.AppointmentEditCommand;
import by.dubrovskaya.command.patient.PatientDeleteCommand;
import by.dubrovskaya.command.user.*;
import by.dubrovskaya.domain.enumeration.CommandType;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static final CommandFactory instance = new CommandFactory();
    private final Map<CommandType, Command> commands = new HashMap<>();

    private CommandFactory() {
        commands.put(CommandType.MAIN, new MainCommand());
        commands.put(CommandType.LOGIN, new LoginCommand());
        commands.put(CommandType.LOGOUT, new LogoutCommand());
        commands.put(CommandType.LANGUAGE, new ChangeLanguageCommand());

        commands.put(CommandType.DOCTOR_SAVE, new DoctorSaveCommand());
        commands.put(CommandType.DOCTOR_LIST, new DoctorListCommand());
        commands.put(CommandType.DOCTOR_EDIT, new DoctorEditCommand());
        commands.put(CommandType.DOCTOR_DELETE, new DoctorDeleteCommand());

        commands.put(CommandType.USER_SAVE, new UserSaveCommand());
        commands.put(CommandType.USER_EDIT, new UserEditCommand());
        commands.put(CommandType.USER_LIST, new UserListCommand());
        commands.put(CommandType.USER_DELETE, new UserDeleteCommand());

        commands.put(CommandType.VACATION_SAVE, new VacationSaveCommand());
        commands.put(CommandType.VACATION_LIST, new VacationListCommand());
        commands.put(CommandType.VACATION_EDIT, new VacationEditCommand());
        commands.put(CommandType.VACATION_DELETE, new VacationDeleteCommand());

        commands.put(CommandType.PATIENT_SAVE, new PatientSaveCommand());
        commands.put(CommandType.PATIENT_LIST, new PatientListCommand());
        commands.put(CommandType.PATIENT_EDIT, new PatientEditCommand());
        commands.put(CommandType.PATIENT_DELETE, new PatientDeleteCommand());

        commands.put(CommandType.APPOINTMENT_SAVE, new AppointmentSaveCommand());
        commands.put(CommandType.APPOINTMENT_EDIT, new AppointmentEditCommand());
        commands.put(CommandType.APPOINTMENT_INFO, new AppointmentInfoCommand());
        commands.put(CommandType.APPOINTMENT_LIST, new AppointmentListCommand());
        commands.put(CommandType.APPOINTMENT_GENERATE, new GenerateAppointmentsCommand());
        commands.put(CommandType.APPOINTMENT_CHOICE, new AppointmentChoiceCommand());
        commands.put(CommandType.APPOINTMENT_MEDICAL_CARD, new MedicalCardCommand());
        commands.put(CommandType.APPOINTMENT_CURRENT, new PatientCurrentAppointmentsCommand());
    }

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(String name) throws IllegalArgumentException {
        CommandType commandType = CommandType.getEnum(name);
        return commands.get(commandType);
    }

}
