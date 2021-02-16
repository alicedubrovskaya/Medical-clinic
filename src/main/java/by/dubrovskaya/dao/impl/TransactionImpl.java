package by.dubrovskaya.dao.impl;

import by.dubrovskaya.dao.*;
import by.dubrovskaya.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionImpl implements Transaction {
    private final Connection connection;
    private final Logger logger = LogManager.getLogger(getClass().getName());

    public TransactionImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UserDao createUserDao() {
        BaseDaoImpl dao = new UserDaoImpl();
        dao.setConnection(connection);
        return (UserDao) dao;
    }

    @Override
    public DoctorDao createDoctorDao() {
        BaseDaoImpl dao = new DoctorDaoImpl();
        dao.setConnection(connection);
        return (DoctorDao) dao;
    }

    @Override
    public PatientDao createPatientDao() {
        BaseDaoImpl dao = new PatientDaoImpl();
        dao.setConnection(connection);
        return (PatientDao) dao;
    }

    @Override
    public AppointmentDao createAppointmentDao() {
        BaseDaoImpl dao = new AppointmentDaoImpl();
        dao.setConnection(connection);
        return (AppointmentDao) dao;
    }

    @Override
    public VacationDao createVacationDao() {
        BaseDaoImpl dao = new VacationDaoImpl();
        dao.setConnection(connection);
        return (VacationDao) dao;
    }


    @Override
    public void commit() throws PersistentException {
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.error("It is impossible to commit transaction", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void rollback() throws PersistentException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("It is impossible to rollback transaction", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void setWithoutAutoCommit() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
