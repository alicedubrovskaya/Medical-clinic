package controller;

import dao.database.DoctorDaoImpl;
import domain.Doctor;
import exception.PersistentException;

public class Runner {
    public static void main(String[] args) {
        DoctorDaoImpl doctorDao = new DoctorDaoImpl();
//        Doctor doctor = new Doctor();
//        doctor.setId(1);
//        doctor.setName("Alisa");
//        doctor.setSurname("Dubrovskaya");
//        doctor.setSpecialization("Травматолог");
        try {
            doctorDao.readBySpecializationType("Травматолог");
        } catch (PersistentException e) {
            e.printStackTrace();
        }
    }
}
