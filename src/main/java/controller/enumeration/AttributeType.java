package controller.enumeration;

public enum AttributeType {
    MESSAGE("message"),
    MENU("menu"),

    ID("id"),
    USER("user"),
    PATIENT("patient"),
    VACATION("vacation"),
    DOCTOR("doctor"),
    USER_AUTHORIZED("authorizedUser"),
    REGISTRATION("registration"),
    LANGUAGE("language"),

    DOCTORS("doctors"),
    PATIENTS("patients"),
    USERS("users"),
    VACATIONS("vacations"),
    APPOINTMENT("appointment"),
    APPOINTMENTS("appointments"),
    APPOINTMENT_ID("appointmentId"),
    PATIENT_ID("patientId"),
    STATUSES("statuses"),
    DISEASES("diseases"),
    SPECIALIZATIONS("specializations"),
    NUMBER_OF_PAGES("noOfPages"),
    CURRENT_PAGE("currentPage"),
    WORKING_SHIFTS("workingShifts");

    private final String name;

    AttributeType(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}
