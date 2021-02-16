package by.dubrovskaya.dao;

import by.dubrovskaya.dao.impl.TransactionImpl;

public interface TransactionFactory {
    TransactionImpl createTransaction();

    void close();
}
