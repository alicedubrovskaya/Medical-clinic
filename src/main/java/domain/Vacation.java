package domain;

import java.util.Date;

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
}
