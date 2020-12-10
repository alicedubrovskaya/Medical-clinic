package dao;

import domain.Appointment;
import exception.PersistentException;

import java.sql.Date;
import java.util.List;

public interface AppointmentDao extends Dao<Appointment> {
    List<Appointment> readByTime(Date date) throws PersistentException;
}
