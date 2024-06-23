package com.example.demo.dao;

import com.example.demo.entity.Task;
import com.example.demo.exception.DaoException;

import java.util.Date;
import java.util.List;

public interface TaskDao {
    boolean insert(Task task) throws DaoException;

    boolean update(Task task) throws DaoException;

    boolean delete(int taskId) throws DaoException;

    Task findById(int taskId) throws DaoException;

    List<Task> findAll() throws DaoException;

    boolean updateTitle(int taskId, String title) throws DaoException;

    boolean updateDescription(int taskId, String description) throws DaoException;

    boolean updateDueDate(int taskId, Date dueDate) throws DaoException;

    boolean updateStatus(int taskId, Task.Status status) throws DaoException;
}
