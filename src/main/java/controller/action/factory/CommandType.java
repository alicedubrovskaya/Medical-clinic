package controller.action.factory;

public enum CommandType {
    MAIN("/main"),
    LOGIN("/login"),
    LOGOUT("/logout"),
    LANGUAGE("/language"),
    HOME("/home"),

    DOCTOR_SAVE("/doctor/save"),
    DOCTOR_LIST("/doctor/list"),
    DOCTOR_EDIT("/doctor/edit"),
    DOCTOR_DELETE("/doctor/delete"),

    USER_SAVE("/user/save"),
    USER_EDIT("/user/edit"),
    USER_LIST("/user/list"),
    USER_DELETE("/user/delete"),

    VACATION_SAVE("/vacation/save"),
    VACATION_LIST("/vacation/list"),
    VACATION_EDIT("/vacation/edit"),
    VACATION_DELETE("/vacation/delete"),

    PATIENT_SAVE("/patient/save"),
    PATIENT_LIST("/patient/list"),
    PATIENT_EDIT("/patient/edit"),

    APPOINTMENT_SAVE("/appointment/save"),
    APPOINTMENT_EDIT("/appointment/edit"),
    APPOINTMENT_INFO("/appointment/info"),
    APPOINTMENT_LIST("/appointment/list"),
    APPOINTMENT_GENERATE("/appointment/generate"),
    APPOINTMENT_CHOICE("/appointment/choice"),
    APPOINTMENT_DOCTOR_CHOICE("/appointment/doctor/choice"),
    APPOINTMENT_MEDICAL_CARD("/appointment/medicalCard");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static CommandType getEnum(String type) {
        for (CommandType info : values()) {
            if (info.getCommand().equals(type)) {
                return info;
            }
        }
        throw new IllegalArgumentException("No enum found with type: [" + type + "]");
    }
}
