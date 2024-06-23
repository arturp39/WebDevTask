package com.example.demo.command.impl;

import com.example.demo.command.Command;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.TaskService;
import com.example.demo.service.impl.TaskServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateTaskDescriptionCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String taskIdStr = request.getParameter("task_id");
        String description = request.getParameter("description");

        int taskId = Integer.parseInt(taskIdStr);

        TaskService taskService = TaskServiceImpl.getInstance();
        try {
            taskService.updateTaskDescription(taskId, description);
            logger.debug("Task description updated");
        } catch (ServiceException e) {
            logger.error("Failed to update task description", e);
            throw new CommandException("Failed to update task description", e);
        }
        return "/controller?command=list_tasks";
    }
}
