package controller;

import dao.TransactionFactory;
import dao.database.TransactionFactoryImpl;
import dao.pool.ConnectionPool;
import domain.Doctor;
import exception.PersistentException;
import service.DoctorService;
import service.ServiceFactory;
import service.impl.ServiceFactoryImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ClinicServlet", urlPatterns = "/clinic")
public class ClinicServlet extends HttpServlet {

    String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    String DB_URL = "jdbc:mysql://localhost:3306/clinic_db?serverTimezone=Europe/Minsk";
    String DB_USER = "root";
    String DB_PASSWORD = "pass";
    int DB_POOL_START_SIZE = 10;
    int DB_POOL_MAX_SIZE = 1000;
    int DB_POOL_CHECK_CONNECTION_TIMEOUT = 0;

    TransactionFactory transactionFactory;
    ServiceFactory serviceFactory;

    public ClinicServlet() {
        try {
            ConnectionPool.getInstance().init(DB_DRIVER_CLASS, DB_URL, DB_USER, DB_PASSWORD, DB_POOL_START_SIZE,
                    DB_POOL_MAX_SIZE, DB_POOL_CHECK_CONNECTION_TIMEOUT);
            transactionFactory = new TransactionFactoryImpl();
            serviceFactory = new ServiceFactoryImpl(transactionFactory);
        } catch (PersistentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp");
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/jsp/doctor/edit.jsp");
        view.forward(request, response);
    }

//    @Override
// Doctors list
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html");
//
//        DoctorService doctorService = serviceFactory.getDoctorService();
//        try {
//            List<Doctor> doctors = doctorService.findAll();
//            request.setAttribute("doctors", doctors);
//
//            RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/jsp/doctor/list.jsp");
//            view.forward(request, response);
//        } catch (PersistentException e) {
//            e.printStackTrace();
//        }
//    }
}
