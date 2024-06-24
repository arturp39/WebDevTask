package com.example.demo.command.impl.task;

import com.example.demo.command.Command;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.TaskService;
import com.example.demo.service.impl.TaskServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateTaskTitleCommand implements Command {
    private final static Logger logger = LogManager.getLogger(UpdateTaskTitleCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String taskIdStr = request.getParameter("task_id");
        String title = request.getParameter("title");

        int taskId = Integer.parseInt(taskIdStr);

        TaskService taskService = TaskServiceImpl.getInstance();
        try {
            taskService.updateTaskTitle(taskId, title);
            logger.debug("Task title updated");
            return "/controller?command=list_tasks"; // Redirect to list tasks
        } catch (ServiceException e) {
            logger.error("Failed to update task title", e);
            throw new CommandException("Failed to update task title", e);
        }
    }
}
