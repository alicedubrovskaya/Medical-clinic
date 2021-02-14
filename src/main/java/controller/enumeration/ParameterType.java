package controller.enumeration;

public enum ParameterType {
    ID("id"),
    REGISTRATION("registration"),
    PASSWORD_OLD("old_password"),
    LANGUAGE("language"),
    APPOINTMENT_ID("appointmentId"),
    PAGE("page"),
    SPECIALIZATION("specialization"),
    STATUS("status"),
    DOCTOR_ID("doctorId");

    private final String name;

    ParameterType(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}
