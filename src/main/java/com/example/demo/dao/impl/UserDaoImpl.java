package com.example.demo.dao.impl;

import com.example.demo.dao.BaseDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.exception.DaoException;
import com.example.demo.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;


public class UserDaoImpl extends BaseDao<User> implements UserDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_LOGIN_PASSWORD = "SELECT password from users where name = ?";
    private static final String INSERT_USER = "INSERT INTO users (name, password) VALUES (?, ?)";
    private static final String DELETE_USER = "DELETE FROM users WHERE name = ? AND password = ?";
    private static final String SELECT_USER_BY_LOGIN = "SELECT id, name, password FROM users WHERE name = ?";
    public static final String UPDATE_USER = "UPDATE users SET name = ?, password = ? WHERE id = ?";
    private static UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {

    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            int rowsInserted = statement.executeUpdate();
            logger.debug("Rows inserted for user {}: {}", user.getName(), rowsInserted);
            return rowsInserted > 0;
        } catch (SQLException e) {
            logger.error("SQLException in UserDaoImpl.insert: {}", e.getMessage());
            throw new DaoException("SQL insert user failed", e);
        }
    }

    @Override
    public boolean delete(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            int rowsDeleted = statement.executeUpdate();
            logger.debug("Rows deleted for user {}: {}", user.getName(), rowsDeleted);
            return rowsDeleted > 0;
        } catch (SQLException e) {
            logger.error("SQLException in UserDaoImpl.delete: {}", e.getMessage());
            throw new DaoException("SQL delete user failed", e);
        }
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User update(User user) throws DaoException {
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
//            statement.setString(1, user.getName());
//            statement.setString(2, user.getPassword());
//            statement.setInt(3, user.getId());
//            int rowsUpdated = statement.executeUpdate();
//            logger.debug("Rows updated for user {}: {}", user.getName(), rowsUpdated);
//            return rowsUpdated > 0 ? user : null;
//        } catch (SQLException e) {
//            logger.error("SQLException in UserDaoImpl.update: {}", e.getMessage());
//            throw new DaoException("SQL update user failed", e);
//        }
        return null;
    }

    @Override
    public boolean authenticate(String login, String password) throws DaoException {
        boolean match = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_LOGIN_PASSWORD)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            String passFromDb;
            if (resultSet.next()) {
                passFromDb = resultSet.getString(1);
                match = password.equals(passFromDb);
            }
        } catch (SQLException e) {
            throw new DaoException("sql authenticate failed", e);
        }
        return match;
    }

    public User findByLogin(String login) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("password")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("SQLException in UserDaoImpl.findByLogin: {}", e.getMessage());
            throw new DaoException("SQL find user by login failed", e);
        }
    }
}
