package by.dubrovskaya.service.impl;

import by.dubrovskaya.dao.Transaction;
import by.dubrovskaya.dao.TransactionFactory;
import by.dubrovskaya.dao.impl.TransactionFactoryImpl;
import by.dubrovskaya.dao.pool.ConnectionPool;
import by.dubrovskaya.exception.PersistentException;
import by.dubrovskaya.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Service factory.
 */
public class ServiceFactoryImpl implements ServiceFactory {
    private final TransactionFactory factory;

    private final Logger logger = LogManager.getLogger(getClass().getName());

    public ServiceFactoryImpl(TransactionFactory factory) {
        this.factory = factory;
    }

    @Override
    public UserService getUserService() {
        ServiceImpl service = new UserServiceImpl();
        Transaction transaction = factory.createTransaction();
        service.setTransaction(transaction);
        return (UserService) service;
    }

    @Override
    public PatientService getPatientService() {
        ServiceImpl service = new PatientServiceImpl();
        Transaction transaction = factory.createTransaction();
        service.setTransaction(transaction);
        return (PatientService) service;
    }

    @Override
    public DoctorService getDoctorService() {
        ServiceImpl service = new DoctorServiceImpl();
        Transaction transaction = factory.createTransaction();
        service.setTransaction(transaction);
        return (DoctorService) service;
    }

    @Override
    public AppointmentService getAppointmentService() {
        ServiceImpl service = new AppointmentServiceImpl();
        Transaction transaction = factory.createTransaction();
        service.setTransaction(transaction);
        return (AppointmentService) service;
    }

    @Override
    public VacationService getVacationService() {
        ServiceImpl service = new VacationServiceImpl();
        Transaction transaction = factory.createTransaction();
        service.setTransaction(transaction);
        return (VacationService) service;
    }

    @Override
    public void close() {
        factory.close();
    }
}
