package by.dubrovskaya.domain;

import by.dubrovskaya.domain.enumeration.Role;
import by.dubrovskaya.domain.enumeration.Shift;

public class Doctor extends Person {
    private String specialization;
    private Shift workingShift;

    public static class DoctorBuilder {
        private final Doctor newDoctor;

        public DoctorBuilder() {
            newDoctor = new Doctor();
        }

        public DoctorBuilder setId(Integer id) {
            newDoctor.setId(id);
            return this;
        }

        public DoctorBuilder setLogin(String login) {
            newDoctor.setLogin(login);
            return this;
        }

        public DoctorBuilder setPassword(String password) {
            newDoctor.setPassword(password);
            return this;
        }

        public DoctorBuilder setRole(Role role) {
            newDoctor.setRole(role);
            return this;
        }

        public DoctorBuilder setName(String name) {
            newDoctor.setName(name);
            return this;
        }

        public DoctorBuilder setSurname(String surname) {
            newDoctor.setSurname(surname);
            return this;
        }

        public DoctorBuilder setSpecialization(String specialization) {
            newDoctor.specialization = specialization;
            return this;
        }

        public DoctorBuilder setWorkingShift(Shift workingShift) {
            newDoctor.workingShift = workingShift;
            return this;
        }

        public Doctor build() {
            return newDoctor;
        }

    }

    //TODO

    public String getSpecialization() {
        return specialization;
    }

    public Shift getWorkingShift() {
        return workingShift;
    }

    public void setWorkingShift(Shift workingShift) {
        this.workingShift = workingShift;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
