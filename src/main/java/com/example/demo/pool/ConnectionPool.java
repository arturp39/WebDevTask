package com.example.demo.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/java_web";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1234";
    private static final int POOL_SIZE = 8;

    private static ConnectionPool instance;
    private BlockingQueue<Connection> free;
    private BlockingQueue<Connection> used;

    static {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConnectionPool(int poolSize) {
        free = new LinkedBlockingQueue<>(poolSize);
        used = new LinkedBlockingQueue<>(poolSize);
        Properties prop = new Properties();
        prop.put("user", DB_USERNAME);
        prop.put("password", DB_PASSWORD);
        prop.put("characterEncoding", "UTF-8");
        for (int i = 0; i < poolSize; i++) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(DB_URL, prop);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (connection != null) {
                free.add(connection);
            }
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            int poolSize = Integer.parseInt(System.getProperty("db.poolsize", String.valueOf(POOL_SIZE)));
            instance = new ConnectionPool(poolSize);
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = free.take();
            used.put(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            if (connection != null) {
                used.remove(connection);
                free.put(connection);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void destroyPool() {
        for (int i = 0; i < free.size(); i++) {
            try {
                Connection connection = free.take();
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            java.sql.Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
