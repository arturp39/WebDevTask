package com.example.demo.service;

import com.example.demo.entity.Image;
import com.example.demo.exception.ServiceException;

import java.util.List;

public interface ImageService {
    void addImage(Image image) throws ServiceException;
    void deleteImage(int imageId) throws ServiceException;
    List<Image> listImages(int userId) throws ServiceException;
    void changeImageName(int imageId, String newName) throws ServiceException;
}
