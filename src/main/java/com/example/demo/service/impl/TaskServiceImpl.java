package com.example.demo.service.impl;

import com.example.demo.dao.TaskDao;
import com.example.demo.dao.impl.TaskDaoImpl;
import com.example.demo.entity.Task;
import com.example.demo.exception.DaoException;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.TaskService;

import java.sql.Date;
import java.util.List;

public class TaskServiceImpl implements TaskService {
    private static final TaskServiceImpl instance = new TaskServiceImpl();

    private TaskServiceImpl() {
    }

    public static TaskServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean addTask(Task task) throws ServiceException {
        TaskDao taskDao = TaskDaoImpl.getInstance();
        try {
            return taskDao.insert(task);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateTask(Task task) throws ServiceException {
        return false;
    }

    @Override
    public boolean deleteTask(int taskId) throws ServiceException {
        TaskDao taskDao = TaskDaoImpl.getInstance();
        try {
            return taskDao.delete(taskId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Task getTaskById(int taskId) throws ServiceException {
        TaskDao taskDao = TaskDaoImpl.getInstance();
        try {
            return taskDao.findById(taskId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Task> getAllTasks() throws ServiceException {
        TaskDao taskDao = TaskDaoImpl.getInstance();
        try {
            return taskDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateTaskTitle(int taskId, String title) throws ServiceException {
        TaskDao taskDao = TaskDaoImpl.getInstance();
        try {
            taskDao.updateTitle(taskId, title);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateTaskDescription(int taskId, String description) throws ServiceException {
        TaskDao taskDao = TaskDaoImpl.getInstance();
        try {
            taskDao.updateDescription(taskId, description);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateTaskDueDate(int taskId, Date dueDate) throws ServiceException {
        TaskDao taskDao = TaskDaoImpl.getInstance();
        try {
            taskDao.updateDueDate(taskId, dueDate);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateTaskStatus(int taskId, Task.Status status) throws ServiceException {
        TaskDao taskDao = TaskDaoImpl.getInstance();
        try {
            taskDao.updateStatus(taskId, status);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
