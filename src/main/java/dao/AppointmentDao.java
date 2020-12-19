package dao;

import domain.Appointment;
import exception.PersistentException;


import java.util.Date;
import java.util.List;

public interface AppointmentDao extends Dao<Appointment> {
    List<Appointment> readByTime(Date date) throws PersistentException;

    Appointment readByPatientAndDisease(Integer patientId, String diseaseName) throws PersistentException;
}
