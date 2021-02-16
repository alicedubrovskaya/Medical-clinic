package by.dubrovskaya.domain.enumeration;

public enum Shift {
    FIRST("Первая"),
    SECOND("Вторая");

    private String name;

    Shift(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return ordinal();
    }

    public static Shift getById(Integer id) {
        return Shift.values()[id];
    }

    public static Shift getEnum(String type) {
        for (Shift info : values()) {
            if (info.getName().equals(type)) {
                return info;
            }
        }
        throw new IllegalArgumentException("No enum found with type: [" + type + "]");
    }
}
