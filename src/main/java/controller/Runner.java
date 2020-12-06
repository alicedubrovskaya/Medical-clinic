package controller;

import dao.mysql.TransactionFactoryImpl;
import exception.PersistentException;
import service.MedicalCardService;
import service.ServiceFactory;
import service.impl.MedicalCardServiceImpl;
import service.impl.ServiceFactoryImpl;

public class Runner {
    public static void main(String[] args) {
        try {
            ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
            MedicalCardService service = serviceFactory.getService(MedicalCardService.class);
            service.findById(1);
        } catch (PersistentException e) {
            e.printStackTrace();
        }
    }
}
