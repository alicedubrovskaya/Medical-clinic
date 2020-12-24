package service.impl;

import dao.Transaction;
import dao.TransactionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.*;

public class ServiceFactoryImpl implements ServiceFactory {
    private final TransactionFactory factory;
    private final Logger logger = LogManager.getLogger(getClass().getName());

    public ServiceFactoryImpl(TransactionFactory factory) {
        this.factory = factory;
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

    @Override
    public void close() {
        factory.close();
    }
}
