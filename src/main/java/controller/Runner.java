package controller;

import dao.database.DoctorDaoImpl;
import domain.Doctor;
import domain.Specialization;
import exception.PersistentException;

public class Runner {
    public static void main(String[] args) {
        DoctorDaoImpl doctorDao = new DoctorDaoImpl();
        Doctor doctor =new Doctor();
        doctor.setId(2);
        doctor.setName("Алиса");
        doctor.setSpecialization(Specialization.CARDIOLOGIST);
        doctor.setSurname("Дубровская");
        try {
            doctorDao.update(doctor);
        } catch (PersistentException e) {
            e.printStackTrace();
        }
    }
}
