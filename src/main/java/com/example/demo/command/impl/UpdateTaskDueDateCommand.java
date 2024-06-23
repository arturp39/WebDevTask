package com.example.demo.command.impl;

import com.example.demo.command.Command;
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

public class UpdateTaskDueDateCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String taskIdStr = request.getParameter("task_id");
        String dueDateStr = request.getParameter("due_date");

        int taskId = Integer.parseInt(taskIdStr);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dueDate;
        try {
            dueDate = sdf.parse(dueDateStr);
        } catch (ParseException e) {
            logger.error("Invalid date format", e);
            throw new CommandException("Invalid date format", e);
        }

        java.sql.Date sqlDueDate = new java.sql.Date(dueDate.getTime());

        TaskService taskService = TaskServiceImpl.getInstance();
        try {
            taskService.updateTaskDueDate(taskId, sqlDueDate);
            logger.debug("Deadline date updated");

        } catch (ServiceException e) {
            logger.error("Failed to update task due date", e);
            throw new CommandException("Failed to update task due date", e);
        }

        return "/controller?command=list_tasks";
    }
}
