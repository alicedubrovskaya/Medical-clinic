package domain;

public enum Status {
    WAS("был"),
    MISSED("пропустил");

    private String name;

    private Status(String name) {
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
