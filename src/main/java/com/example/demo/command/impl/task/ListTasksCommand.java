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

import java.util.List;

public class ListTasksCommand implements Command {
    private final static Logger logger = LogManager.getLogger(ListTasksCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        TaskService taskService = TaskServiceImpl.getInstance();
        try {
            List<Task> tasks = taskService.getAllTasks();
            request.setAttribute("tasks", tasks);
            logger.debug("Tasks retrieved successfully");
            return "/pages/list_tasks.jsp"; // Forward to list tasks page
        } catch (ServiceException e) {
            logger.error("Failed to retrieve tasks", e);
            throw new CommandException("Failed to retrieve tasks", e);
        }
    }
}
