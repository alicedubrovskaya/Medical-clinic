package controller.action.factory;

import controller.action.*;
import controller.action.admin.*;
import controller.action.authorized.*;
import controller.action.doctor.AppointmentEditCommand;
import controller.action.doctor.AppointmentsChoiceCommand;
import controller.action.patient.AppointmentChoiceCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static final CommandFactory instance = new CommandFactory();
    private final Map<CommandType, Command> commands = new HashMap<>();

    private CommandFactory() {
        commands.put(CommandType.MAIN, new MainCommand());
        commands.put(CommandType.LOGIN, new LoginCommand());

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

        commands.put(CommandType.APPOINTMENT_SAVE, new AppointmentSaveCommand());
        commands.put(CommandType.APPOINTMENT_EDIT, new AppointmentEditCommand());
        commands.put(CommandType.APPOINTMENT_INFO, new AppointmentInfoCommand());
        commands.put(CommandType.APPOINTMENT_LIST, new AppointmentListCommand());
        commands.put(CommandType.APPOINTMENT_GENERATE, new GenerateAppointmentsCommand());
        commands.put(CommandType.APPOINTMENT_CHOICE, new AppointmentChoiceCommand());
        commands.put(CommandType.APPOINTMENT_DOCTOR_CHOICE, new AppointmentsChoiceCommand());
        commands.put(CommandType.APPOINTMENT_MEDICAL_CARD, new MedicalCardCommand());
    }

    //TODO
//        commands.put(CommandType.ERROR, new )


    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(String name) throws IllegalArgumentException{
        CommandType commandType = CommandType.getEnum(name);
        return commands.get(commandType);
    }

}
