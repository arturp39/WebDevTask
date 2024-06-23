package com.example.demo.command.impl;

import com.example.demo.command.Command;
import com.example.demo.entity.Image;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.ImageService;
import com.example.demo.service.impl.ImageServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

public class AddImageCommand implements Command {
    private final ImageService imageService = ImageServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String name = request.getParameter("name");
        Part filePart;
        try {
            filePart = request.getPart("file");
        } catch (IOException | ServletException e) {
            throw new CommandException("Failed to get file part", e);
        }
        String filePath = saveFile(filePart);
        Image image = new Image(0, name, filePath, (int) request.getSession().getAttribute("userId"));
        try {
            imageService.addImage(image);
        } catch (ServiceException e) {
            throw new CommandException("Failed to add image", e);
        }
        return "image_added.jsp";
    }

    private String saveFile(Part filePart) throws CommandException {
        String fileName = filePart.getSubmittedFileName();
        String uploadDir = "/path/to/upload/directory"; // Update this path as needed
        File file = new File(uploadDir, fileName);
        try {
            filePart.write(file.getAbsolutePath());
        } catch (IOException e) {
            throw new CommandException("Failed to save file", e);
        }
        return file.getAbsolutePath();
    }
}
