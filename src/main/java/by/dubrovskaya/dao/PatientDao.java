package by.dubrovskaya.dao;

import by.dubrovskaya.domain.Patient;
import by.dubrovskaya.domain.User;
import by.dubrovskaya.exception.PersistentException;

import java.util.List;
import java.util.Map;

public interface PatientDao extends Dao<Patient> {
    List<Patient> read() throws PersistentException;

    Patient readByEmail(String email) throws PersistentException;

    List<String> readDiseases() throws PersistentException;

    List<String> readDiseasesByPatient(Integer patientId) throws PersistentException;

    String readDiseaseByAppointment(Integer patientId, Integer appointmentId) throws PersistentException;

    Map<Integer, List<Patient>> read(int offset, int noOfRecords) throws PersistentException;

    void saveDiseaseForPatient(Integer patientId, Integer appointmentId, String disease) throws PersistentException;
}
