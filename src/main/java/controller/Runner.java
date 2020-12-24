package controller;

import dao.TransactionFactory;
import dao.database.TransactionFactoryImpl;
import dao.pool.ConnectionPool;
import exception.PersistentException;
import service.PatientService;
import service.ServiceFactory;
import service.impl.ServiceFactoryImpl;

public class Runner {
    public static void main(String[] args) {
        try {
            String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
            String DB_URL = "jdbc:mysql://localhost:3306/clinic_db?serverTimezone=Europe/Minsk";
            String DB_USER = "root";
            String DB_PASSWORD = "pass";
            int DB_POOL_START_SIZE = 10;
            int DB_POOL_MAX_SIZE = 1000;
            int DB_POOL_CHECK_CONNECTION_TIMEOUT = 0;


            ConnectionPool.getInstance().init(DB_DRIVER_CLASS, DB_URL, DB_USER, DB_PASSWORD, DB_POOL_START_SIZE,
                    DB_POOL_MAX_SIZE, DB_POOL_CHECK_CONNECTION_TIMEOUT);

            TransactionFactory transactionFactory = new TransactionFactoryImpl();
            ServiceFactory serviceFactory = new ServiceFactoryImpl(transactionFactory);
//            AppointmentService appointmentService = serviceFactory.getService(AppointmentService.class);
//            DoctorService doctorService = serviceFactory.getService(DoctorService.class);
//            Calendar calendar = new GregorianCalendar(2020, 11 , 20);
//            appointmentService.createAppointmentsForDoctors(calendar.getTime(), 30);

            PatientService patientService = serviceFactory.getPatientService();
            patientService.findById(1);

        } catch (PersistentException e) {
            e.printStackTrace();
        }
    }
}
