package dao.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionFactory {
    private static final String PROPERTY_PATH = "database.properties";
    private static final String DB_DRIVER = "db.driver";
    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_POOL_SIZE = "db.poolSize";
    private final Logger logger = LogManager.getLogger(getClass().getName());

    public void init(ConnectionPool connectionPool) {
        try {
            Properties properties = new Properties();
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(PROPERTY_PATH);
            properties.load(inputStream);

            Class.forName(properties.getProperty(DB_DRIVER));
            connectionPool.url = properties.getProperty(DB_URL);
            connectionPool.user = properties.getProperty(DB_USER);
            connectionPool.password = properties.getProperty(DB_PASSWORD);
            connectionPool.size = Integer.parseInt(properties.getProperty(DB_POOL_SIZE));
        } catch (ClassNotFoundException | IOException e) {
            logger.error(e);
        }
    }
}

