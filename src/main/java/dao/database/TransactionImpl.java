package dao.database;

import dao.*;
import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionImpl implements Transaction {
    private final Logger logger = LogManager.getLogger(getClass().getName());

    private static Map<Class<? extends Dao<?>>, Class<? extends BaseDaoImpl>> classes = new ConcurrentHashMap<>();

    static {
        classes.put(MedicalCardDao.class, MedicalCardDaoImpl.class);
    }

    private Connection connection;

    public TransactionImpl(Connection connection) {
        this.connection = connection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <Type extends Dao<?>> Type createDao(Class<Type> key) throws PersistentException {
//        Class<? extends BaseDaoImpl> value = classes.get(key);
//        if (value != null) {
//            try {
//                BaseDaoImpl dao = value.newInstance();
//                dao.setConnection(connection);
//                return (Type) dao;
//            } catch (InstantiationException | IllegalAccessException e) {
//                logger.error("It is impossible to create data access object", e);
//                throw new PersistentException(e);
//            }
//        }
        return null;
    }

    @Override
    public void commit() throws PersistentException {
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.error("It is impossible to commit transaction", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void rollback() throws PersistentException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("It is impossible to rollback transaction", e);
            throw new PersistentException(e);
        }
    }
}
