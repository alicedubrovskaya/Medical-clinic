package dao.database;

import dao.TransactionFactory;
import dao.pool.ConnectionPool;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionFactoryImpl implements TransactionFactory {
    private final Connection connection;
    private static final Logger logger = LogManager.getLogger(TransactionFactoryImpl.class);


    public TransactionFactoryImpl() throws PersistentException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error("Can't initialize transactions");
            throw new PersistentException(e);
        }
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
        }
    }
}
