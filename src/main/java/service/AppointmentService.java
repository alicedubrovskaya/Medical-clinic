package service;

import domain.Appointment;
import exception.PersistentException;

import java.util.Date;
import java.util.List;

public interface AppointmentService extends Service {
    void save(Appointment appointment) throws PersistentException;

    Appointment findById(Integer id) throws PersistentException;

    List<Appointment> findByTime(Date date) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
