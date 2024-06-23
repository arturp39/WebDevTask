package com.example.demo.dao;

import com.example.demo.entity.Image;
import com.example.demo.exception.DaoException;

import java.util.List;

public interface ImageDao {
    List<Image> findByUserId(int userId) throws DaoException;
    void updateName(int imageId, String newName) throws DaoException;
}
