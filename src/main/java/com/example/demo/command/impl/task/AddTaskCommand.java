package com.example.demo.command.impl.task;

import com.example.demo.command.Command;
import com.example.demo.entity.Task;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.TaskService;
import com.example.demo.service.impl.TaskServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTaskCommand implements Command {
    private final static Logger logger = LogManager.getLogger(AddTaskCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDateStr = request.getParameter("due_date");
        String statusStr = request.getParameter("status");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date dueDate;
        try {
            dueDate = sdf.parse(dueDateStr);
        } catch (ParseException e) {
            logger.error("Invalid date format");
            throw new CommandException("Invalid date format", e);
        }
        Task.Status status;
        try {
            status = Task.Status.valueOf(statusStr);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid status value");
            throw new CommandException("Invalid status value", e);
        }

        Task task = new Task(title, description, dueDate, status);
        TaskService taskService = TaskServiceImpl.getInstance();
        try {
            if (taskService.addTask(task)) {
                logger.info("Task " + task.getTitle() + " created successfully");
                return "/controller?command=list_tasks"; // Redirect to list tasks
            } else {
                request.setAttribute("addTask_msg", "Failed to add task");
                logger.error("Error adding task");
                return "/pages/add_task.jsp"; // Forward to add task page
            }
        } catch (ServiceException e) {
            logger.error("Error adding task", e);
            throw new CommandException("Failed to add task", e);
        }
    }
}
