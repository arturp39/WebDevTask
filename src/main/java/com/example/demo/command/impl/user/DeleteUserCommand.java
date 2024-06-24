package com.example.demo.command.impl.user;

import com.example.demo.command.Command;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteUserCommand implements Command {
    private final static Logger logger = LogManager.getLogger(DeleteUserCommand.class);
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");
        try {
            logger.debug("Attempting to delete user: {}", login);
            if (userService.deleteUser(login, pass)) {
                logger.debug("User deleted successfully: {}", login);
                return "/index.jsp"; // Redirect to index page
            } else {
                request.setAttribute("delete_msg", "Deletion failed. Try again.");
                logger.debug("Deletion failed for user: {}", login);
                return "/pages/delete_user.jsp"; // Forward to delete user page
            }
        } catch (ServiceException e) {
            logger.error("ServiceException during user deletion: {}", e.getMessage());
            throw new CommandException("Failed to delete user", e);
        }
    }
}
