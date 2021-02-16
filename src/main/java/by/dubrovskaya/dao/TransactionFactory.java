package by.dubrovskaya.dao;

import by.dubrovskaya.dao.impl.TransactionImpl;

import java.sql.Connection;

public interface TransactionFactory {
    TransactionImpl createTransaction(Connection connection);
}
