package by.dubrovskaya.domain;

public abstract class Person extends User {
    private String surname;
    private String name;

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }
}
