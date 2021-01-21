package controller.action.factory;

import controller.action.*;
import controller.action.admin.appointment.GenerateAppointmentsAction;
import controller.action.admin.doctor.DoctorDeleteAction;
import controller.action.admin.doctor.DoctorListAction;
import controller.action.admin.doctor.DoctorSaveAction;
import controller.action.admin.patient.PatientListAction;
import controller.action.admin.user.UserDeleteAction;
import controller.action.admin.user.UserListAction;
import controller.action.admin.vacation.VacationDeleteAction;
import controller.action.admin.vacation.VacationEditAction;
import controller.action.admin.vacation.VacationListAction;
import controller.action.admin.vacation.VacationSaveAction;
import controller.action.authorized.*;
import controller.action.doctor.AppointmentEditAction;
import controller.action.doctor.AppointmentsChoiceAction;
import controller.action.patient.AppointmentChoiceAction;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static final CommandFactory instance = new CommandFactory();
    private final Map<CommandType, Action> commands = new HashMap<>();

    private CommandFactory() {
        commands.put(CommandType.MAIN, new MainAction());
        commands.put(CommandType.LOGIN, new LoginAction());

        commands.put(CommandType.DOCTOR_SAVE, new DoctorSaveAction());
        commands.put(CommandType.DOCTOR_LIST, new DoctorListAction());
        commands.put(CommandType.DOCTOR_EDIT, new DoctorEditAction());
        commands.put(CommandType.DOCTOR_DELETE, new DoctorDeleteAction());

        commands.put(CommandType.USER_SAVE, new UserSaveAction());
        commands.put(CommandType.USER_EDIT, new UserEditAction());
        commands.put(CommandType.USER_LIST, new UserListAction());
        commands.put(CommandType.USER_DELETE, new UserDeleteAction());

        commands.put(CommandType.VACATION_SAVE, new VacationSaveAction());
        commands.put(CommandType.VACATION_LIST, new VacationListAction());
        commands.put(CommandType.VACATION_EDIT, new VacationEditAction());
        commands.put(CommandType.VACATION_DELETE, new VacationDeleteAction());

        commands.put(CommandType.PATIENT_SAVE, new PatientSaveAction());
        commands.put(CommandType.PATIENT_LIST, new PatientListAction());
        commands.put(CommandType.PATIENT_EDIT, new PatientEditAction());

        commands.put(CommandType.APPOINTMENT_SAVE, new AppointmentSaveAction());
        commands.put(CommandType.APPOINTMENT_EDIT, new AppointmentEditAction());
        commands.put(CommandType.APPOINTMENT_INFO, new AppointmentInfoAction());
        commands.put(CommandType.APPOINTMENT_LIST, new AppointmentListAction());
        commands.put(CommandType.APPOINTMENT_GENERATE, new GenerateAppointmentsAction());
        commands.put(CommandType.APPOINTMENT_CHOICE, new AppointmentChoiceAction());
        commands.put(CommandType.APPOINTMENT_DOCTOR_CHOICE, new AppointmentsChoiceAction());
        commands.put(CommandType.APPOINTMENT_MEDICAL_CARD, new MedicalCardAction());
    }

    //TODO
//        commands.put(CommandType.ERROR, new )


    public static CommandFactory getInstance() {
        return instance;
    }

    public Action getCommand(String name) throws IllegalArgumentException{
        CommandType commandType = CommandType.getEnum(name);
        return commands.get(commandType);
    }

}
