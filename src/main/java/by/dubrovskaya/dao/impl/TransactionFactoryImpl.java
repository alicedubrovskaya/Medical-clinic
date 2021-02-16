package by.dubrovskaya.dao.impl;

import by.dubrovskaya.dao.TransactionFactory;
import by.dubrovskaya.dao.pool.ConnectionPool;
import by.dubrovskaya.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionFactoryImpl implements TransactionFactory {
    private final Connection connection;
    private static TransactionFactoryImpl instance;

    private static final Logger logger = LogManager.getLogger(TransactionFactoryImpl.class);

    private TransactionFactoryImpl() throws PersistentException {
        this.connection = ConnectionPool.getInstance().getConnection();
    }

    public static TransactionFactoryImpl getInstance() throws PersistentException {
        if (instance == null) {
            instance = new TransactionFactoryImpl();
        }
        return instance;
    }

    @Override
    public TransactionImpl createTransaction() {
        return new TransactionImpl(connection);
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Connection cannot be closed");
        }
    }
}
