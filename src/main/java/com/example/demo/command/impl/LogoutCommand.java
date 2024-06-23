package com.example.demo.command.impl;

import com.example.demo.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "index.jsp";
    }
}
