package com.example.demo.dao.impl;

import com.example.demo.dao.BaseDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.exception.DaoException;
import com.example.demo.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class UserDaoImpl extends BaseDao<User> implements UserDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_LOGIN_PASSWORD = "SELECT password from users where name = ?";
    private static final String INSERT_USER = "INSERT INTO users (name, password) VALUES (?, ?)";
    private static final String DELETE_USER = "DELETE FROM users WHERE name = ? AND password = ?";
    private static final String SELECT_USER_BY_LOGIN = "SELECT id, name, password FROM users WHERE name = ?";

    private static final UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {

    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection(); PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            int rowsInserted = statement.executeUpdate();
            logger.debug("User added: " + user.getName());
            return rowsInserted > 0;
        } catch (SQLException e) {
            logger.error("SQL insert user failed", e);
            throw new DaoException("SQL insert user failed", e);
        }
    }

    @Override
    public boolean delete(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            int rowsDeleted = statement.executeUpdate();
            logger.debug("User deleted: " + user.getName());
            return rowsDeleted > 0;
        } catch (SQLException e) {
            logger.error("SQL delete user failed", e);
            throw new DaoException("SQL delete user failed", e);
        }
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User update(User user) throws DaoException {
        return null;
    }

    @Override
    public boolean authenticate(String login, String password) throws DaoException {
        boolean match = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection(); PreparedStatement statement = connection.prepareStatement(SELECT_LOGIN_PASSWORD)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            String passFromDb;
            if (resultSet.next()) {
                passFromDb = resultSet.getString(1);
                match = password.equals(passFromDb);
                logger.debug("User" + login + "authenticated successfully");
            }
        } catch (SQLException e) {
            throw new DaoException("Sql authenticate failed", e);
        }
        return match;
    }

    @Override
    public User findByLogin(String login) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection(); PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("password"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("SQL find user by login failed", e);
            throw new DaoException("SQL find user by login failed", e);
        }
    }
}
