package by.dubrovskaya.dao.impl;

import by.dubrovskaya.dao.TransactionFactory;
import by.dubrovskaya.dao.pool.ConnectionPool;
import by.dubrovskaya.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionFactoryImpl implements TransactionFactory {
    private Connection connection;
    private static final Logger logger = LogManager.getLogger(TransactionFactoryImpl.class);

    public TransactionFactoryImpl() throws PersistentException {
        connection = ConnectionPool.getInstance().getConnection();
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
