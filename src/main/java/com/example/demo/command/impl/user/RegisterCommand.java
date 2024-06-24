package com.example.demo.command.impl.user;

import com.example.demo.command.Command;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegisterCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");
        try {
            logger.debug("Attempting to register user: {}", login);
            if (userService.addUser(login, pass)) {
                logger.debug("User {} registered successfully", login);
                return "/index.jsp";
            } else {
                request.setAttribute("register_msg", "Registration failed");
                logger.debug("Registration failed for user: {}", login);
                return "/pages/register.jsp";
            }
        } catch (ServiceException e) {
            logger.error("ServiceException during registration", e);
            throw new CommandException("Failed to add user", e);
        }
    }
}
