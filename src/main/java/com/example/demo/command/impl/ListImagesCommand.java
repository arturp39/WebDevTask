package com.example.demo.command.impl;

import com.example.demo.command.Command;
import com.example.demo.entity.Image;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.ImageService;
import com.example.demo.service.impl.ImageServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class ListImagesCommand implements Command {
    private final ImageService imageService = ImageServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        List<Image> images;
        try {
            images = imageService.listImages((int) request.getSession().getAttribute("userId"));
        } catch (ServiceException e) {
            throw new CommandException("Failed to list images", e);
        }
        request.setAttribute("images", images);
        return "listImages.jsp";
    }
}
