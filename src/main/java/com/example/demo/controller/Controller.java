package com.example.demo.controller;

import com.example.demo.command.Command;
import com.example.demo.command.CommandType;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.pool.ConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "Controller", urlPatterns = {"/controller", "*.do"})
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
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid command");
            return;
        }

        String page;
        try {
            page = command.execute(request);
            logger.debug("Forwarding to page: {}", page);
            request.getRequestDispatcher(page).forward(request, response);
        } catch (CommandException | ServiceException e) {
            logger.error("Exception during command execution: {}", e.getMessage());
            throw new ServletException(e);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().destroyPool();
    }
}
