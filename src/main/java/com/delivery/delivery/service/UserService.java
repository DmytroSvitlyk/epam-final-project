package com.delivery.delivery.service;

import com.delivery.delivery.dao.DAOException;
import com.delivery.delivery.dao.DAOFactory;
import com.delivery.delivery.dao.UserDAO;
import com.delivery.delivery.model.User;
import com.delivery.delivery.util.PasswordEncoder;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class UserService {

    Logger logger = Logger.getLogger(UserService.class);

    private static UserService instance;

    private UserService(){
    }

    public static synchronized UserService getInstance() {
        if(instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private UserDAO getUserDAO() {
        return DAOFactory.getDAOFactory().getUserDao();
    }

    public User login(String login, String password) {
        UserDAO dao = getUserDAO();
        try {
            User user = dao.getByLogin(login);
            if (PasswordEncoder.encodePassword(password).equals(user.getPassword())) {
                return user;
            }
        } catch (DAOException e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    public User register(User user) {
        UserDAO dao = getUserDAO();
        try {
            if(dao.canRegisterUser(user)) {
                user.setPassword(PasswordEncoder.encodePassword(user.getPassword()));
                return getUserDAO().insertUser(user);
            }
        } catch (DAOException e) {
            logger.info("Unable to register user: " + e.getMessage());
            throw new ServiceException(e);
        }
        return null;
    }

    public void deleteUser(User user) {
        UserDAO dao = getUserDAO();
        try {
            getUserDAO().deleteUser(user);
        } catch (DAOException e) {
            logger.warn("Unable to delete user (id : " + user.getId() + " login : " + user.getLogin() + ")");
            throw new ServiceException(e);
        }
    }

    public void updateUser(User user) {
        UserDAO dao = getUserDAO();
        try {
            getUserDAO().updateUser(user);
        } catch (DAOException e) {
            logger.warn("Unable to update user (id : " + user.getId() + " login : " + user.getLogin() + ")");
            throw new ServiceException(e);
        }
    }

}
