package com.example.demo.command.impl;

import com.example.demo.command.Command;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.ImageService;
import com.example.demo.service.impl.ImageServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class DeleteImageCommand implements Command {
    private final ImageService imageService = ImageServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        int imageId = Integer.parseInt(request.getParameter("imageId"));
        try {
            imageService.deleteImage(imageId);
        } catch (ServiceException e) {
            throw new CommandException("Failed to delete image", e);
        }
        return "image_deleted.jsp";
    }
}
