package by.dubrovskaya.dao.pool;

import by.dubrovskaya.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {
    private final Logger logger = LogManager.getLogger(getClass().getName());
    private static final String UNABLE_TO_CONNECT = "It is impossible to connect to a database";

    private static final ReentrantLock lock = new ReentrantLock();
    private Semaphore semaphore;
    protected String url;
    protected String user;
    protected String password;
    protected Integer size;

    protected final BlockingQueue<ProxyConnection> freeConnections = new LinkedBlockingQueue<>();
    private final BlockingQueue<ProxyConnection> usedConnections = new LinkedBlockingQueue<>();

    private static AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static ConnectionPool instance = null;

    public static ConnectionPool getInstance() {
        if (!isInitialized.get()) {
            try {
                lock.lock();
                if (!isInitialized.get()) {
                    instance = new ConnectionPool();
                    isInitialized.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private ConnectionPool() {
        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.init(this);
            for (int counter = 0; counter < size; counter++) {
                freeConnections.put(createConnection());
            }
            this.semaphore = new Semaphore(size);
        } catch (SQLException | InterruptedException e) {
            logger.error(e);
        }
    }

    public Connection getConnection() throws PersistentException {
        ProxyConnection connection;
        boolean permit;
        try {
            permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
            if (permit) {
                connection = freeConnections.poll();
                usedConnections.put(Objects.requireNonNull(connection));
            } else {
                throw new PersistentException("Time of waiting to acquire semaphore is exceeded");
            }
        } catch (InterruptedException e) {
            logger.error(UNABLE_TO_CONNECT, e);
            throw new PersistentException(e);

        }
        logger.debug("Connection was received from pool. Current pool size: {} used connections; {} free connection",
                usedConnections.size(), freeConnections.size());
        return connection;
    }

    void freeConnection(ProxyConnection connection) {
        try {
            connection.clearWarnings();
            connection.setAutoCommit(true);
            boolean isRemoved = usedConnections.remove(connection);
            freeConnections.put(connection);
            logger.debug("Connection was returned into pool. Current pool size: {} used connections; {} free connection",
                    usedConnections.size(), freeConnections.size());
            semaphore.release();
            if (!isRemoved) {
                logger.warn("Connection wasn't removed from used connections");
            }
        } catch (InterruptedException | SQLException e) {
            logger.warn("It is impossible to return database connection into pool", e);
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
