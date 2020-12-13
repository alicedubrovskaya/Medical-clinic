package domain.enumeration;

public enum Status {
    WAS("был"),
    MISSED("пропустил");

    private String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return ordinal();
    }

    public static Status getById(Integer id) {
        return Status.values()[id];
    }

}
