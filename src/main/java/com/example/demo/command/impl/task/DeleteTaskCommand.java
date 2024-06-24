package com.example.demo.command.impl.task;

import com.example.demo.command.Command;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.TaskService;
import com.example.demo.service.impl.TaskServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteTaskCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String taskIdStr = request.getParameter("task_id");
        if (taskIdStr == null) {
            throw new CommandException("Missing task ID");
        }
        int taskId = Integer.parseInt(taskIdStr);
        TaskService taskService = TaskServiceImpl.getInstance();
        try {
            boolean deleted = taskService.deleteTask(taskId);
            if (deleted) {
                logger.info("Task deleted successfully: " + taskId);
                return "/controller?command=list_tasks";
            } else {
                throw new CommandException("Failed to delete task");
            }
        } catch (ServiceException e) {
            logger.error("Task deletion failed", e);
            throw new CommandException("Task deletion failed", e);
        }
    }
}
