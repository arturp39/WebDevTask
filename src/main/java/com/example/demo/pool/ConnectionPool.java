package com.example.demo.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/java_web";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1234";
    private static final int DEFAULT_POOL_SIZE = 16;

    private static volatile ConnectionPool instance;

    static {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            logger.error("Error registering driver");
            throw new ExceptionInInitializerError(e);
        }
    }

    private final BlockingQueue<Connection> free;
    private final BlockingQueue<Connection> used;

    private ConnectionPool(int poolSize) {
        free = new LinkedBlockingQueue<>(poolSize);
        used = new LinkedBlockingQueue<>(poolSize);
        Properties prop = getDbProperties();
        for (int i = 0; i < poolSize; i++) {
            try {
                Connection connection = DriverManager.getConnection(DB_URL, prop);
                free.add(connection);
            } catch (SQLException e) {
                logger.error("Failed to create connection", e);
            }
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    int poolSize = Integer.parseInt(System.getProperty("db.poolsize", String.valueOf(DEFAULT_POOL_SIZE)));
                    instance = new ConnectionPool(poolSize);
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = free.take();
            if (!isValid(connection)) {
                connection = DriverManager.getConnection(DB_URL, getDbProperties());
            }
            used.put(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted while waiting for a connection", e);
        } catch (SQLException e) {
            logger.error("Failed to get a valid connection", e);
        }
        return connection;
    }
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed() && isValid(connection)) {
                    used.remove(connection);
                    free.put(connection);
                } else {
                    used.remove(connection);
                    Connection newConnection = DriverManager.getConnection(DB_URL, getDbProperties());
                    free.put(newConnection);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (SQLException e) {
                logger.error("Failed to release connection", e);
            }
        }
    }

    public void destroyPool() {
        closeConnections(free);
        closeConnections(used);
    }

    private void closeConnections(BlockingQueue<Connection> queue) {
        for (Connection connection : queue) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error("Failed to close connection", e);
            }
        }
    }

    public void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.info("Unregistering driver");
            } catch (SQLException e) {
                logger.error("Error unregistering driver", e);
            }
        }
    }

    private boolean isValid(Connection connection) {
        try {
            return connection != null && connection.isValid(2);
        } catch (SQLException e) {
            logger.error("Connection validation failed", e);
            return false;
        }
    }

    private Properties getDbProperties() {
        Properties prop = new Properties();
        prop.put("user", DB_USERNAME);
        prop.put("password", DB_PASSWORD);
        prop.put("characterEncoding", "UTF-8");
        return prop;
    }
}
