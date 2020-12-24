package dao;

import dao.database.TransactionImpl;

public interface TransactionFactory {
    TransactionImpl createTransaction();

    void close();
}
