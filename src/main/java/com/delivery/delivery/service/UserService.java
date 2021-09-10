package com.delivery.delivery.service;

import com.delivery.delivery.dao.DAOException;
import com.delivery.delivery.dao.DAOFactory;
import com.delivery.delivery.dao.UserDAO;
import com.delivery.delivery.model.User;
import com.delivery.delivery.util.PasswordEncoder;
import org.apache.log4j.Logger;
import java.util.regex.Pattern;

public class UserService {

    Logger logger = Logger.getLogger(UserService.class);

    private static UserService instance;

    private static final String LOGIN_REGEX = "^[a-zA-Z][a-zA-Z_0-9]+$";
    private static final String EMAIL_REGEX = "[A-Za-z0-9+_.-]+@[a-zA-Z0-9]\\.[a-zA-Z]+$";
    private static final String NAME_REGEX = "^[a-z ,.'-]+$";

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
            throw new DAOException("Wrong Login or Password");
        } catch (DAOException e) {
            logger.info(e.getMessage());
            throw new ServiceException(e);
        }
    }

    public User register(User user) {
        UserDAO dao = getUserDAO();
        try {
            checkUserData(user);
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

    public void checkUserData(User user) {
        if(user == null) {
            throw new ServiceException("User is null");
        }
        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
        if(!emailPattern.matcher(user.getEmail()).matches()) {
            throw new ServiceException("Invalid email");
        }
        Pattern loginPattern = Pattern.compile(LOGIN_REGEX);
        if(!loginPattern.matcher(user.getLogin()).matches()) {
            throw new ServiceException("Invalid symbols in login or login is too short");
        }
        Pattern namePattern = Pattern.compile(NAME_REGEX, Pattern.UNICODE_CHARACTER_CLASS);
        if(!namePattern.matcher(user.getFirstName()).matches()) {
            throw new ServiceException("Invalid First Name");
        }
        if(!namePattern.matcher(user.getSecondName()).matches()) {
            throw new ServiceException("Invalid Second Name");
        }
    }

}
