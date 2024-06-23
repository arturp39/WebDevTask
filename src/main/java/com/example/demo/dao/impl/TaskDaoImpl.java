package com.example.demo.dao.impl;

import com.example.demo.dao.TaskDao;
import com.example.demo.entity.Task;
import com.example.demo.exception.DaoException;
import com.example.demo.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskDaoImpl implements TaskDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String INSERT_TASK = "INSERT INTO tasks (title, description, due_date, status) VALUES (?, ?, ?, ?::task_status)";
    private static final String UPDATE_TASK = "UPDATE tasks SET title = ?, description = ?, due_date = ?, status = ?::task_status WHERE id = ?";
    private static final String DELETE_TASK = "DELETE FROM tasks WHERE id = ?";
    private static final String SELECT_TASK_BY_ID = "SELECT * FROM tasks WHERE id = ?";
    private static final String SELECT_ALL_TASKS = "SELECT * FROM tasks";
    private static final String UPDATE_TITLE = "UPDATE tasks SET title = ? WHERE id = ?";
    private static final String UPDATE_DESCRIPTION = "UPDATE tasks SET description = ? WHERE id = ?";
    private static final String UPDATE_DUE_DATE = "UPDATE tasks SET due_date = ? WHERE id = ?";
    private static final String UPDATE_STATUS = "UPDATE tasks SET status = ?::task_status WHERE id = ?";
    private static TaskDaoImpl instance = new TaskDaoImpl();

    private TaskDaoImpl() {
    }

    public static TaskDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(Task task) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_TASK, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setDate(3, new java.sql.Date(task.getDueDate().getTime()));
            statement.setString(4, task.getStatus().name());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        task.setId(generatedKeys.getInt(1));
                    }
                }
            }
            logger.debug("Task added: "+ task.getTitle());
            return rowsInserted > 0;
        } catch (SQLException e) {
            logger.error("SQL insert failed", e);
            throw new DaoException("SQL insert failed", e);
        }
    }

    @Override
    public boolean update(Task task) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TASK)) {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setDate(3, new java.sql.Date(task.getDueDate().getTime()));
            statement.setString(4, task.getStatus().name());
            statement.setInt(5, task.getId());
            int rowsUpdated = statement.executeUpdate();
            logger.debug("Task updated:"+ task.getTitle());
            return rowsUpdated > 0;
        } catch (SQLException e) {
            logger.error("SQL update failed", e);
            throw new DaoException("SQL update failed", e);
        }
    }

    @Override
    public boolean delete(int taskId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_TASK)) {
            statement.setInt(1, taskId);
            int rowsDeleted = statement.executeUpdate();
            logger.debug("Task deleted:"+ taskId);
            return rowsDeleted > 0;
        } catch (SQLException e) {
            logger.error("SQL delete failed", e);
            throw new DaoException("SQL delete failed", e);
        }
    }

    @Override
    public Task findById(int taskId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_TASK_BY_ID)) {
            statement.setInt(1, taskId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Task(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getDate("due_date"),
                            Task.Status.valueOf(resultSet.getString("status")));
                }
            }
        } catch (SQLException e) {
            logger.error("SQL find task by ID failed", e);
            throw new DaoException("SQL find task by ID failed", e);
        }
        return null;
    }

    @Override
    public List<Task> findAll() throws DaoException {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TASKS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Task task = new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("due_date"),
                        Task.Status.valueOf(resultSet.getString("status")));
                tasks.add(task);
            }
        } catch (SQLException e) {
            logger.error("SQL find all tasks failed", e);
            throw new DaoException("SQL find all tasks failed", e);
        }
        return tasks;
    }

    @Override
    public boolean updateTitle(int taskId, String title) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TITLE)) {
            statement.setString(1, title);
            statement.setInt(2, taskId);
            int rowsUpdated = statement.executeUpdate();
            logger.debug("Title updated for task:"+ taskId);
            return rowsUpdated > 0;
        } catch (SQLException e) {
            logger.error("SQL update title failed", e);
            throw new DaoException("SQL update title failed", e);
        }
    }

    @Override
    public boolean updateDescription(int taskId, String description) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DESCRIPTION)) {
            statement.setString(1, description);
            statement.setInt(2, taskId);
            int rowsUpdated = statement.executeUpdate();
            logger.debug("Description updated for task:"+ taskId);
            return rowsUpdated > 0;
        } catch (SQLException e) {
            logger.error("SQL update task description failed", e);
            throw new DaoException("SQL update task description failed", e);
        }
    }

    @Override
    public boolean updateDueDate(int taskId, Date dueDate) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DUE_DATE)) {
            statement.setDate(1, new java.sql.Date(dueDate.getTime()));
            statement.setInt(2, taskId);
            int rowsUpdated = statement.executeUpdate();
            logger.debug("Date updated for task:"+ taskId);
            return rowsUpdated > 0;
        } catch (SQLException e) {
            logger.error("SQL update task due date failed", e);
            throw new DaoException("SQL update task due date failed", e);
        }
    }

    @Override
    public boolean updateStatus(int taskId, Task.Status status) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS)) {
            statement.setString(1, status.name());
            statement.setInt(2, taskId);
            int rowsUpdated = statement.executeUpdate();
            logger.debug("Status updated for task:"+ taskId);
            return rowsUpdated > 0;
        } catch (SQLException e) {
            logger.error("SQL update task status failed", e);
            throw new DaoException("SQL update task status failed", e);
        }
    }
}
