package by.dubrovskaya.domain;

import by.dubrovskaya.domain.enumeration.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Patient extends Person {
    private String email;
    private String phoneNumber;
    private String address;
    private List<String> diseases = new ArrayList<>();

    public static class Builder {
        private Patient newPatient;

        public Builder() {
            newPatient = new Patient();
        }

        public Builder setId(Integer id) {
            newPatient.setId(id);
            return this;
        }

        public Builder setLogin(String login) {
            newPatient.setLogin(login);
            return this;
        }

        public Builder setPassword(String password) {
            newPatient.setPassword(password);
            return this;
        }

        public Builder setRole(Role role) {
            newPatient.setRole(role);
            return this;
        }

        public Builder setName(String name) {
            newPatient.setName(name);
            return this;
        }

        public Builder setSurname(String surname) {
            newPatient.setSurname(surname);
            return this;
        }

        public Builder setEmail(String email) {
            newPatient.email = email;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            newPatient.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setAddress(String address) {
            newPatient.address = address;
            return this;
        }

        public Patient build() {
            return newPatient;
        }

    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getDiseases() {
        return diseases;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        if (!super.equals(o)) return false;
        Patient patient = (Patient) o;
        return Objects.equals(email, patient.email) && Objects.equals(phoneNumber, patient.phoneNumber) && Objects.equals(address, patient.address) && Objects.equals(diseases, patient.diseases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, phoneNumber, address, diseases);
    }
}
