package by.dubrovskaya.dao.impl;

import by.dubrovskaya.dao.TransactionFactory;
import by.dubrovskaya.dao.pool.ConnectionPool;
import by.dubrovskaya.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionFactoryImpl implements TransactionFactory {
    private static TransactionFactoryImpl instance;

    private static final Logger logger = LogManager.getLogger(TransactionFactoryImpl.class);

    private TransactionFactoryImpl() {
    }

    public static TransactionFactoryImpl getInstance() {
        if (instance == null) {
            instance = new TransactionFactoryImpl();
        }
        return instance;
    }

    @Override
    public TransactionImpl createTransaction(Connection connection) {
        return new TransactionImpl(connection);
    }

}
