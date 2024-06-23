//package com.example.demo.command.impl;
//
//import com.example.demo.command.Command;
//import com.example.demo.exception.CommandException;
//import com.example.demo.exception.ServiceException;
//import com.example.demo.service.UserService;
//import com.example.demo.service.impl.UserServiceImpl;
//import jakarta.servlet.http.HttpServletRequest;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//public class UpdateUserCommand implements Command {
//    private static final Logger logger = LogManager.getLogger(UpdateUserCommand.class);
//
//    @Override
//    public String execute(HttpServletRequest request) throws CommandException, ServiceException {
//        String newLogin = request.getParameter("new_login");
//        String newPass = request.getParameter("new_pass");
//
//        if (newLogin == null || newPass == null) {
//            logger.error("Missing parameters for updating user");
//            throw new CommandException("Missing parameters for updating user");
//        }
//
//        UserService userService = UserServiceImpl.getInstance();
//        boolean isUpdated;
//
//        try {
//            isUpdated = userService.updateUser(newLogin, newPass);
//        } catch (ServiceException e) {
//            logger.error("Failed to update user", e);
//            throw new CommandException("Failed to update user", e);
//        }
//
//        if (isUpdated) {
//            request.setAttribute("update_msg", "User successfully updated.");
//        } else {
//            request.setAttribute("update_msg", "Failed to update user.");
//        }
//
//        return "/pages/update_user.jsp";
//    }
//}
