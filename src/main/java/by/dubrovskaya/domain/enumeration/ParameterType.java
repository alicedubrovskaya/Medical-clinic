package by.dubrovskaya.domain.enumeration;

public enum ParameterType {
    ID("id"),
    REGISTRATION("registration"),
    PASSWORD_OLD("old_password"),
    LANGUAGE("language"),
    APPOINTMENT_ID("appointmentId"),
    PATIENT_ID("patientId"),
    PAGE("page"),
    DOCTOR_ID("doctorId"),

    COMPLAINTS("complaints"),
    MEDICAL_REPORT("medicalReport"),
    RECOMMENDATION("recommendation"),
    DISEASES("diseases"),
    STATUS("status"),

    DATE("date"),
    DAYS("days"),

    NAME("name"),
    SURNAME("surname"),
    SPECIALIZATION("specialization"),
    WORKING_SHIFT("workingShift"),

    EMAIL("email"),
    PHONE_NUMBER("phoneNumber"),
    ADDRESS("address"),

    LOGIN("login"),
    PASSWORD("password"),
    ROLE("role"),

    VACATION_START("vacation-start"),
    VACATION_END("vacation-end");

    private final String name;

    ParameterType(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}
