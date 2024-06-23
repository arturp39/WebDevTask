package com.example.demo.service;

import com.example.demo.exception.ServiceException;

public interface UserService {
    boolean authenticate(String login, String pass) throws ServiceException;

    boolean addUser(String login, String pass) throws ServiceException;

    boolean deleteUser(String login, String pass) throws ServiceException;
}
