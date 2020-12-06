package dao;

import exception.PersistentException;

public interface TransactionFactory {
	Transaction createTransaction() throws PersistentException;

	void close();
}
