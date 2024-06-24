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

public class UpdateTaskStatusCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String taskIdStr = request.getParameter("task_id");
        String statusStr = request.getParameter("status");

        if (taskIdStr == null || statusStr == null) {
            throw new CommandException("Missing task ID or status");
        }

        int taskId = Integer.parseInt(taskIdStr);
        Task.Status status;
        try {
            status = Task.Status.valueOf(statusStr);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid status value", e);
            throw new CommandException("Invalid status value", e);
        }
        TaskService taskService = TaskServiceImpl.getInstance();
        try {
            taskService.updateTaskStatus(taskId, status);
            logger.debug("Task status updated");
            return "/controller?command=list_tasks";
        } catch (ServiceException e) {
            logger.error("Failed to update task status", e);
            throw new CommandException("Failed to update task status", e);
        }
    }
}
