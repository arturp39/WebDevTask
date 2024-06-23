package com.example.demo.service.impl;


import com.example.demo.dao.impl.UserDaoImpl;
import com.example.demo.entity.User;
import com.example.demo.exception.DaoException;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;


public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static UserServiceImpl instance = new UserServiceImpl();

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
            logger.error("Error authenticating user: {}", e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addUser(String login, String pass) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            if (userDao.findByLogin(login) != null) {
                logger.debug("User already exists: {}", login);
                return false;
            }
            String hashedPassword = BCrypt.hashpw(pass, BCrypt.gensalt());
            User user = new User(login, hashedPassword);
            boolean result = userDao.insert(user);
            logger.debug("User {} added with result: {}", login, result);
            return result;
        } catch (DaoException e) {
            logger.error("Error adding user: {}", e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteUser(String login, String pass) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            User user = userDao.findByLogin(login);
            if (user == null) {
                logger.debug("User not found: {}", login);
                return false;
            }
            if (!BCrypt.checkpw(pass, user.getPassword())) {
                logger.debug("Password mismatch for user: {}", login);
                return false;
            }
            boolean result = userDao.delete(user);
            logger.debug("User {} deleted with result: {}", login, result);
            return result;
        } catch (DaoException e) {
            logger.error("Error deleting user: {}", e.getMessage());
            throw new ServiceException(e);
        }
    }
}
