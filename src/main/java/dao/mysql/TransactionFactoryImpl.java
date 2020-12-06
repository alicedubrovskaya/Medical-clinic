package dao.mysql;

import dao.Transaction;
import dao.TransactionFactory;
import dao.pool.ConnectionPool;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionFactoryImpl implements TransactionFactory {
	private final Logger logger = LogManager.getLogger(getClass().getName());
	private Connection connection;
	
	public TransactionFactoryImpl() throws PersistentException {
		connection = ConnectionPool.getInstance().getConnection();
		try {
			connection.setAutoCommit(false);
		} catch(SQLException e) {
			logger.error("It is impossible to turn off autocommiting for database connection", e);
			throw new PersistentException(e);
		}
	}

	@Override
	public Transaction createTransaction() throws PersistentException {
		return new TransactionImpl(connection);
	}

	@Override
	public void close() {
		try {
			connection.close();
		} catch(SQLException e) {}
	}
}
