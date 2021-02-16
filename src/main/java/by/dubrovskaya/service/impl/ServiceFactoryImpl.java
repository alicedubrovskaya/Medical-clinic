package by.dubrovskaya.service.impl;

import by.dubrovskaya.dao.Transaction;
import by.dubrovskaya.dao.TransactionFactory;
import by.dubrovskaya.dao.impl.TransactionFactoryImpl;
import by.dubrovskaya.exception.PersistentException;
import by.dubrovskaya.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Service factory.
 */
public class ServiceFactoryImpl implements ServiceFactory {
    private TransactionFactory factory;
    private final Logger logger = LogManager.getLogger(getClass().getName());

    public ServiceFactoryImpl() {
        try {
            this.factory = TransactionFactoryImpl.getInstance();
        } catch (PersistentException e) {
            logger.error(e);
        }
    }

    @Override
    public UserService getUserService() {
        Transaction transaction = factory.createTransaction();
        ServiceImpl service = new UserServiceImpl();
        service.setTransaction(transaction);
        return (UserService) service;
    }

    @Override
    public PatientService getPatientService() {
        Transaction transaction = factory.createTransaction();
        ServiceImpl service = new PatientServiceImpl();
        service.setTransaction(transaction);
        return (PatientService) service;
    }

    @Override
    public DoctorService getDoctorService() {
        Transaction transaction = factory.createTransaction();
        ServiceImpl service = new DoctorServiceImpl();
        service.setTransaction(transaction);
        return (DoctorService) service;
    }

    @Override
    public AppointmentService getAppointmentService() {
        Transaction transaction = factory.createTransaction();
        ServiceImpl service = new AppointmentServiceImpl();
        service.setTransaction(transaction);
        return (AppointmentService) service;
    }

    @Override
    public VacationService getVacationService() {
        Transaction transaction = factory.createTransaction();
        ServiceImpl service = new VacationServiceImpl();
        service.setTransaction(transaction);
        return (VacationService) service;
    }

    /**
     * Closes transaction factory
     */
    @Override
    public void close() {
        factory.close();
    }
}
