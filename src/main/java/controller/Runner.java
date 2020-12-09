package controller;

import dao.MedicalCardDao;
import dao.database.MedicalCardDaoImpl;
import domain.MedicalCard;
import exception.PersistentException;

public class Runner {
    public static void main(String[] args) {
        MedicalCardDao medicalCardDao = new MedicalCardDaoImpl();
        MedicalCard medicalCard = new MedicalCard();
        medicalCard.setId(1);
        medicalCard.setVaccinations("");
        try {
            medicalCardDao.delete(1);
        } catch (PersistentException e) {
            e.printStackTrace();
        }
    }
}
