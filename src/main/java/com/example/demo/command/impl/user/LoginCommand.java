package com.example.demo.command.impl.user;

import com.example.demo.command.Command;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginCommand implements Command {
    private final static Logger logger = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        try {
            if (userService.authenticate(login, password)) {
                session.setAttribute("user_name", login);
                logger.debug("Login is successful");
                return "/pages/main.jsp"; // Redirect to main page
            } else {
                request.setAttribute("login_msg", "Incorrect login or password");
                return "/index.jsp"; // Forward to login page
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
