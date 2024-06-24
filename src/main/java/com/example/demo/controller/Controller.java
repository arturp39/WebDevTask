package com.example.demo.controller;

import com.example.demo.command.Command;
import com.example.demo.command.CommandType;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "Controller", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String commandStr = request.getParameter("command");
        logger.debug("Received command: {}", commandStr);

        if (commandStr == null || commandStr.isEmpty()) {
            logger.error("No command found in request");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing command parameter");
            return;
        }

        Command command = CommandType.define(commandStr);
        if (command == null) {
            logger.error("No command defined for: {}", commandStr);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid command parameter");
            return;
        }

        try {
            String page = command.execute(request);
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                response.sendRedirect(request.getContextPath() + page);
            } else {
                request.getRequestDispatcher(page).forward(request, response);
            }
        } catch (CommandException | ServiceException e) {
            logger.error("Command execution failed", e);
            throw new ServletException("Command execution failed", e);
        }
    }
}
