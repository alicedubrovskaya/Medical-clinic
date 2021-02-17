package by.dubrovskaya.domain;

import java.util.Date;
import java.util.Objects;

public class Vacation extends Entity {
    private Date start;
    private Date end;
    private Doctor doctor;

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vacation)) return false;
        if (!super.equals(o)) return false;
        Vacation vacation = (Vacation) o;
        return Objects.equals(start, vacation.start) && Objects.equals(end, vacation.end) && Objects.equals(doctor, vacation.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), start, end, doctor);
    }
}
