package domain.enumeration;

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
}
