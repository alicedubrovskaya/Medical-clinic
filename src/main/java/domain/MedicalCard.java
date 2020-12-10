package domain;

import java.util.List;

public class MedicalCard extends Entity {
    private String chronicDiseases;
    private String vaccinations;
    private List<Appointment> appointments;

    public String getChronicDiseases() {
        return chronicDiseases;
    }

    public String getVaccinations() {
        return vaccinations;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setChronicDiseases(String chronicDiseases) {
        this.chronicDiseases = chronicDiseases;
    }

    public void setVaccinations(String vaccinations) {
        this.vaccinations = vaccinations;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
