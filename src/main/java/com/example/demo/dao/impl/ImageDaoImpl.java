package com.example.demo.dao.impl;

import com.example.demo.dao.BaseDao;
import com.example.demo.dao.ImageDao;
import com.example.demo.entity.Image;
import com.example.demo.exception.DaoException;
import com.example.demo.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImageDaoImpl extends BaseDao<Image> implements ImageDao {
    private static final String INSERT_IMAGE = "INSERT INTO images (name, filePath, userId) VALUES (?, ?, ?)";
    private static final String DELETE_IMAGE = "DELETE FROM images WHERE id = ?";
    private static final String SELECT_IMAGES_BY_USER = "SELECT * FROM images WHERE userId = ?";
    private static final String UPDATE_IMAGE_NAME = "UPDATE images SET name = ? WHERE id = ?";
    private static final ImageDaoImpl instance = new ImageDaoImpl();

    private ImageDaoImpl() {
    }

    public static ImageDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(Image image) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_IMAGE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, image.getName());
            statement.setString(2, image.getFilePath());
            statement.setInt(3, image.getUserId());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new DaoException("Inserting image failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    image.setId(generatedKeys.getInt(1));
                } else {
                    throw new DaoException("Inserting image failed, no ID obtained.");
                }
            }
            return true;
        } catch (SQLException e) {
            throw new DaoException("Failed to insert image", e);
        }
    }

    @Override
    public boolean delete(Image image) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_IMAGE)) {
            statement.setInt(1, image.getId());
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete image", e);
        }
    }

    @Override
    public List<Image> findAll() throws DaoException {
        // todo
        return new ArrayList<>();
    }

    @Override
    public Image update(Image image) throws DaoException {
        // todo
        return image;
    }

    @Override
    public List<Image> findByUserId(int userId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_IMAGES_BY_USER)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Image> images = new ArrayList<>();
                while (resultSet.next()) {
                    Image image = new Image();
                    image.setId(resultSet.getInt("id"));
                    image.setName(resultSet.getString("name"));
                    image.setFilePath(resultSet.getString("filePath"));
                    image.setUserId(resultSet.getInt("userId"));
                    images.add(image);
                }
                return images;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find images by user ID", e);
        }
    }

    @Override
    public void updateName(int imageId, String newName) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_IMAGE_NAME)) {
            statement.setString(1, newName);
            statement.setInt(2, imageId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to update image name", e);
        }
    }
}
