package domain;

import domain.enumeration.Shift;

public class Doctor extends Person {
    private String specialization;
    private Shift workingShift;

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
