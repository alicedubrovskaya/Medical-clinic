package dao.pool;

import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {
    private final Logger logger = LogManager.getLogger(getClass().getName());
    private static final String UNABLE_TO_CONNECT = "It is impossible to connect to a database";

    private static final ReentrantLock lock = new ReentrantLock();
    protected String url;
    protected String user;
    protected String password;
    protected Integer startSize;
    protected Integer maxSize;
    protected Integer checkConnectionTimeout;

    protected final BlockingQueue<ProxyConnection> freeConnections = new LinkedBlockingQueue<>();
    private final Set<ProxyConnection> usedConnections = new ConcurrentSkipListSet<>();
    //    private Semaphore semaphore = new Semaphore(maxSize);
    private final ConnectionFactory connectionFactory = new ConnectionFactory();

    private static volatile ConnectionPool instance = null;

    public static ConnectionPool getInstance() {
        if (instance == null) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private ConnectionPool() {
        try {
            connectionFactory.init(this);
            for (int counter = 0; counter < startSize; counter++) {
                freeConnections.put(createConnection());
            }
        } catch (SQLException | InterruptedException e) {
            logger.error(e);
        }
    }

    public Connection getConnection() throws PersistentException {
        //Semaphore.acquire()
        //use poll or pull instead of take

        ProxyConnection connection = null;
        while (connection == null) {
            try {
                if (!freeConnections.isEmpty()) {
                    connection = freeConnections.take();
                    if (!connection.isValid(checkConnectionTimeout)) {
                        try {
                            connection.getConnection().close();
                        } catch (SQLException e) {
                            logger.error(UNABLE_TO_CONNECT, e);
                        }
                        connection = null;
                    }
                } else if (usedConnections.size() < maxSize) {
                    connection = createConnection();
                } else {
                    logger.error("The limit of number of database connections is exceeded");
                    throw new PersistentException();
                }
            } catch (InterruptedException | SQLException e) {
                logger.error(UNABLE_TO_CONNECT, e);
                throw new PersistentException(e);
            }
        }
        usedConnections.add(connection);
        logger.debug("Connection was received from pool. Current pool size: {} used connections; {} free connection",
                usedConnections.size(), freeConnections.size());
        return connection;
    }

    void freeConnection(ProxyConnection connection) {
        try {
            if (connection.isValid(checkConnectionTimeout)) {
                connection.clearWarnings();
                connection.setAutoCommit(true);
                usedConnections.remove(connection);
                freeConnections.put(connection);
                logger.debug("Connection was returned into pool. Current pool size: {} used connections; {} free connection",
                        usedConnections.size(), freeConnections.size());
            }
        } catch (SQLException | InterruptedException e1) {
            logger.warn("It is impossible to return database connection into pool", e1);
            try {
                connection.getConnection().close();
            } catch (SQLException e2) {
                logger.error(UNABLE_TO_CONNECT, e2);
            }
        }
    }


    private ProxyConnection createConnection() throws SQLException {
        return new ProxyConnection(DriverManager.getConnection(url, user, password));
    }

    public void destroy() {
        usedConnections.addAll(freeConnections);
        freeConnections.clear();
        for (ProxyConnection connection : usedConnections) {
            try {
                connection.getConnection().close();
            } catch (SQLException e) {
                logger.error(UNABLE_TO_CONNECT, e);
            }
        }
        usedConnections.clear();
    }
}
