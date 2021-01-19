package domain.enumeration;

public enum Status {
    WAS("Был"),
    MISSED("Не был");
    //TODO пропустил

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

    public static Status getEnum(String type) {
        for (Status info : values()) {
            if (info.getName().equals(type)) {
                return info;
            }
        }
        throw new IllegalArgumentException("No enum found with type: [" + type + "]");
    }

}
