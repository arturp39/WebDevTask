package com.example.demo.service.impl;

import com.example.demo.dao.impl.ImageDaoImpl;
import com.example.demo.entity.Image;
import com.example.demo.exception.DaoException;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.ImageService;

import java.util.List;

public class ImageServiceImpl implements ImageService {
    private static final ImageServiceImpl instance = new ImageServiceImpl();
    private final ImageDaoImpl imageDao = ImageDaoImpl.getInstance();

    private ImageServiceImpl() {}

    public static ImageServiceImpl getInstance() {
        return instance;
    }

    @Override
    public void addImage(Image image) throws ServiceException {
        try {
            imageDao.insert(image);
        } catch (DaoException e) {
            throw new ServiceException("Failed to add image", e);
        }
    }

    @Override
    public void deleteImage(int imageId) throws ServiceException {
        Image image = new Image();
        image.setId(imageId);
        try {
            imageDao.delete(image);
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete image", e);
        }
    }

    @Override
    public List<Image> listImages(int userId) throws ServiceException {
        try {
            return imageDao.findByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException("Failed to list images", e);
        }
    }

    @Override
    public void changeImageName(int imageId, String newName) throws ServiceException {
        try {
            imageDao.updateName(imageId, newName);
        } catch (DaoException e) {
            throw new ServiceException("Failed to change image name", e);
        }
    }
}
