package controller;

import dao.database.PatientDaoImpl;
import domain.Patient;
import exception.PersistentException;

public class Runner {
    public static void main(String[] args) {
        PatientDaoImpl patientDao = new PatientDaoImpl();
        try {
            patientDao.readByEmail("denisik");
        } catch (PersistentException e) {
            e.printStackTrace();
        }
    }
}
