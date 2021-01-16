package domain.enumeration;

public enum Role {
    ADMINISTRATOR("Администратор"),
    DOCTOR("Врач"),
    PATIENT("Пациент");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return ordinal();
    }

    public static Role getById(Integer id) {
        return Role.values()[id];
    }

    public static Role getEnum(String type) {
        for (Role info : values()) {
            if (info.getName().equals(type)) {
                return info;
            }
        }
        throw new IllegalArgumentException("No enum found with type: [" + type + "]");
    }
}
