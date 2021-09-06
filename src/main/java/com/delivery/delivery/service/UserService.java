package com.delivery.delivery.service;

import com.delivery.delivery.dao.DAOException;
import com.delivery.delivery.dao.DAOFactory;
import com.delivery.delivery.dao.UserDAO;
import com.delivery.delivery.model.User;
import org.apache.log4j.Logger;

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

    private UserDAO getDAO() {
        return DAOFactory.getDAOFactory().getUserDao();
    }

    public User login(String login, String password) {
        UserDAO dao = getDAO();
        try {
            User user = dao.getByLogin(login);
            if (password.equals(user.getPassword())) {
                return user;
            }
        } catch (DAOException e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    public User register(User user) {
        return getDAO().insertUser(user);
    }

}
