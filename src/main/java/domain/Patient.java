package domain;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Person{
    private String email;
    private String phoneNumber;
    private String address;
    //TODO class Disease (should contain Doctor, Appointment, Name)
    private List<String> diseases = new ArrayList<>();

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
}
