package dao;

import domain.Patient;
import exception.PersistentException;

import java.util.List;

public interface PatientDao extends Dao<Patient> {
    List<Patient> read() throws PersistentException;

    Patient readByEmail(String email) throws PersistentException;

    List<String> readDiseases() throws PersistentException;

    List<String> readDiseasesByPatient(Integer patientId) throws PersistentException;

    String readDiseaseByAppointment(Integer patientId, Integer appointmentId) throws PersistentException;

    void saveDiseaseForPatient(Integer patientId, Integer appointmentId, String disease) throws PersistentException;
}
