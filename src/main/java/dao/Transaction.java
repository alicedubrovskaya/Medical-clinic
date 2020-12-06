package dao;

import exception.PersistentException;

public interface Transaction {
	<Type extends Dao<?>> Type createDao(Class<Type> key) throws PersistentException;

	void commit() throws PersistentException;

	void rollback() throws PersistentException;
}
