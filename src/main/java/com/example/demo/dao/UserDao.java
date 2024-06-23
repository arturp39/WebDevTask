package com.example.demo.dao;

import com.example.demo.entity.User;
import com.example.demo.exception.DaoException;

public interface UserDao {
    boolean authenticate(String login, String password) throws DaoException;
    boolean insert(User user) throws DaoException;
    boolean delete(User user) throws DaoException;

    User update(User user) throws DaoException;
}
