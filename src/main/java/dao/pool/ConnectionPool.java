package dao.pool;

import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {
    private final Logger logger = LogManager.getLogger(getClass().getName());

    private static final String PROPERTY_PATH = "database.properties";
    private static final String DB_DRIVER = "db.driver";
    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_POOL_START_SIZE = "db.poolStartSize";
    private static final String DB_POOL_MAX_SIZE = "db.poolMaxSize";
    private static final String DB_CHECK_CONNECTION_TIME_OUT = "db.poolCheckConnectionTimeOut";
    private static final String UNABLE_TO_CONNECT = "It is impossible to connect to a database";

    private static final ReentrantLock lock = new ReentrantLock();
    private String url;
    private String user;
    private String password;
    private int maxSize;
    private int checkConnectionTimeout;

    private final BlockingQueue<PooledConnection> freeConnections = new LinkedBlockingQueue<>();
    private final Set<PooledConnection> usedConnections = new ConcurrentSkipListSet<>();

    private static ConnectionPool instance = new ConnectionPool();

    public static ConnectionPool getInstance() {
        try {
            lock.lock();
            if (instance == null) {
                instance = new ConnectionPool();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }

    private ConnectionPool() {
        Properties properties = new Properties();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(PROPERTY_PATH);
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Error while reading properties", e);
        }
        String driverClass = properties.getProperty(DB_DRIVER);
        String urlProperty = properties.getProperty(DB_URL);
        String userProperty = properties.getProperty(DB_USER);
        String passwordProperty = properties.getProperty(DB_PASSWORD);
        int startSizeProperty = Integer.parseInt(properties.getProperty(DB_POOL_START_SIZE));
        int maxSizeProperty = Integer.parseInt(properties.getProperty(DB_POOL_MAX_SIZE));
        int checkConnectionTimeOutProperty = Integer.parseInt(properties.getProperty(DB_CHECK_CONNECTION_TIME_OUT));

        try {
            init(driverClass, urlProperty, userProperty, passwordProperty, startSizeProperty, maxSizeProperty, checkConnectionTimeOutProperty);
        } catch (PersistentException e) {
            logger.error(e);
        }
    }

    public void init(String driverClass, String url, String user, String password, int startSize, int maxSize, int checkConnectionTimeout) throws PersistentException {
        try {
            Class.forName(driverClass);
            this.url = url;
            this.user = user;
            this.password = password;
            this.maxSize = maxSize;
            this.checkConnectionTimeout = checkConnectionTimeout;
            for (int counter = 0; counter < startSize; counter++) {
                freeConnections.put(createConnection());
            }
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            logger.fatal("It is impossible to initialize connection pool", e);
            throw new PersistentException(e);
        }
    }

    public Connection getConnection() throws PersistentException {
        PooledConnection connection = null;
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

    void freeConnection(PooledConnection connection) {
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


    private PooledConnection createConnection() throws SQLException {
        return new PooledConnection(DriverManager.getConnection(url, user, password));
    }

    public void destroy() {
        usedConnections.addAll(freeConnections);
        freeConnections.clear();
        for (PooledConnection connection : usedConnections) {
            try {
                connection.getConnection().close();
            } catch (SQLException e) {
                logger.error(UNABLE_TO_CONNECT, e);
            }
        }
        usedConnections.clear();
    }
}
