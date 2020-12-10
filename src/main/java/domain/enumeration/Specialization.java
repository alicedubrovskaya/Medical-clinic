package domain.enumeration;

public enum Specialization {
    THERAPIST("терапевт"),
    CARDIOLOGIST("кардиолог"),
    RHEUMATOLOGIST("ревматолог");

    private String name;

    private Specialization(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return ordinal();
    }

    public static Specialization getById(Integer id) {
        return Specialization.values()[id];
    }
}