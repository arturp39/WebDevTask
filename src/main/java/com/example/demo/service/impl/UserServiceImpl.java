package com.example.demo.service.impl;


import com.example.demo.dao.impl.UserDaoImpl;
import com.example.demo.entity.User;
import com.example.demo.exception.DaoException;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.UserService;
import org.mindrot.jbcrypt.BCrypt;


public class UserServiceImpl implements UserService {
    private static final UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String login, String pass) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            User user = userDao.findByLogin(login);
            if (user == null) {
                return false;
            }
            return BCrypt.checkpw(pass, user.getPassword());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addUser(String login, String pass) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            if (userDao.findByLogin(login) != null) {
                return false;
            }
            String hashedPassword = BCrypt.hashpw(pass, BCrypt.gensalt());
            User user = new User(login, hashedPassword);
            boolean result = userDao.insert(user);
            return result;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteUser(String login, String pass) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            User user = userDao.findByLogin(login);
            if (user == null) {
                return false;
            }
            if (!BCrypt.checkpw(pass, user.getPassword())) {
                return false;
            }
            boolean result = userDao.delete(user);
            return result;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
