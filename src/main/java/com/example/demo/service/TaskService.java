package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.exception.ServiceException;

import java.sql.Date;
import java.util.List;

public interface TaskService {
    boolean addTask(Task task) throws ServiceException;
    boolean updateTask(Task task) throws ServiceException;
    boolean deleteTask(int taskId) throws ServiceException;
    Task getTaskById(int taskId) throws ServiceException;
    List<Task> getAllTasks() throws ServiceException;
    void updateTaskTitle(int taskId, String title) throws ServiceException;
    void updateTaskDescription(int taskId, String description) throws ServiceException;
    void updateTaskDueDate(int taskId, Date dueDate) throws ServiceException;
    void updateTaskStatus(int taskId, Task.Status status) throws ServiceException;
}
