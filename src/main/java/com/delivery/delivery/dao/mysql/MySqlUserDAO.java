package com.delivery.delivery.dao.mysql;

import com.delivery.delivery.dao.DAOException;
import com.delivery.delivery.dao.UserDAO;
import com.delivery.delivery.model.Role;
import com.delivery.delivery.model.User;
import com.delivery.delivery.dao.pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserDAO implements UserDAO {

    private static Logger logger = Logger.getLogger(MySqlUserDAO.class);

    private static MySqlUserDAO instance;

    private static final String INSERT_USER = "INSERT INTO user (first_name, second_name, login, email, phone, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String GET_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
    private static final String CHECK_FOR_UNIQUE_LOGIN = "SELECT COUNT(*) FROM user WHERE login = ?";
    private static final String CHECK_FOR_UNIQUE_EMAIL = "SELECT COUNT(*) FROM user WHERE email = ?";
    private static final String CHECK_FOR_UNIQUE_PHONE = "SELECT COUNT(*) FROM user WHERE phone = ?";
    private static final String GET_ALL = "SELECT * FROM user";
    private static final String UPDATE_USER = "UPDATE user SET first_name = ?, second_name = ?, login = ?, email = ?, phone = ?, password = ?, role = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";

    private MySqlUserDAO() {

    }

    public static synchronized MySqlUserDAO getInstance() {
        if(instance == null) {
            instance = new MySqlUserDAO();
        }
        return instance;
    }

    @Override
    public User insertUser(User user) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            statement.setString(index++, user.getFirstName());
            statement.setString(index++, user.getSecondName());
            statement.setString(index++, user.getLogin());
            statement.setString(index++, user.getEmail());
            statement.setString(index++, user.getPhone());
            statement.setString(index++, user.getPassword());
            statement.setString(index++, user.getRole().toString());
            statement.execute();
            try(ResultSet set = statement.getGeneratedKeys()) {
                if(set.next()) {
                    user.setId(set.getInt(1));
                }
                else {
                    throw new SQLException("Unable to insert user to database");
                }
            }
        }
        catch (SQLException e) {
            logger.warn("Inserting user to database failed");
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public User getById(int id) {
        User user = null;
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(GET_BY_ID)) {
            statement.setInt(1, id);
            try(ResultSet set = statement.executeQuery()) {
                if(set.next()) {
                    user = parseUser(set);
                }
                else {
                    return null;
                }
            }
        }
        catch (SQLException e) {
            logger.info("Unable to find user by id");
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public User getByLogin(String login) {
        User user = null;
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(GET_BY_LOGIN)) {
            statement.setString(1, login);
            try(ResultSet set = statement.executeQuery()) {
                if(set.next()) {
                    user = parseUser(set);
                }
                else {
                    return null;
                }
            }
        }
        catch (SQLException e) {
            logger.info("Unable to find user by login");
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public boolean canRegisterUser(User user) {
        if(!loginIsUnique(user.getLogin())) {
            throw new DAOException("This login is already taken");
        }
        if(!emailIsUnique(user.getEmail())) {
            throw new DAOException("This email is already taken");
        }
        if(!phoneIsUnique(user.getPhone())) {
            throw new DAOException("This phone is already taken");
        }
        return true;
    }

    private boolean loginIsUnique(String login) {
        return checkForUnique(CHECK_FOR_UNIQUE_LOGIN, login, "login");
    }

    private boolean emailIsUnique(String email) {
        return checkForUnique(CHECK_FOR_UNIQUE_EMAIL, email, "email");
    }

    private boolean phoneIsUnique(String phone) {
        return checkForUnique(CHECK_FOR_UNIQUE_PHONE, phone, "phone");
    }

    private boolean checkForUnique(String query, String value, String field) {
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, value);
            try(ResultSet set = statement.executeQuery()) {
                if(set.next()) {
                    if(set.getInt(1) == 0) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            logger.info("Exception while checking " + field + " " + value + " for unique");
            throw new DAOException(e);
        }
        return false;
    }

    @Override
    public void updateUser(User user) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER)) {
            int index = 1;
            statement.setString(index++, user.getFirstName());
            statement.setString(index++, user.getSecondName());
            statement.setString(index++, user.getLogin());
            statement.setString(index++, user.getEmail());
            statement.setString(index++, user.getPhone());
            statement.setString(index++, user.getPassword());
            statement.setString(index++, user.getRole().toString());
            statement.setInt(index++, user.getId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            logger.warn("Unable to update user");
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteUser(User user) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(DELETE_USER)) {
            statement.setInt(1, user.getId());
            statement.execute();
        } catch (SQLException e) {
            logger.warn("Unable to delete user");
            throw new DAOException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            ResultSet set = conn.createStatement().executeQuery(GET_ALL)) {
            while(set.next()) {
                users.add(parseUser(set));
            }
        } catch (SQLException e) {
            logger.warn("Unable to get users");
            throw new DAOException(e);
        }
        return users;
    }

    private User parseUser(ResultSet set) throws SQLException {
        User user = new User();
        int index = 1;
        user.setId(set.getInt(index++));
        user.setFirstName(set.getString(index++));
        user.setSecondName(set.getString(index++));
        user.setRole(Role.valueOf(set.getString(index++)));
        user.setLogin(set.getString(index++));
        user.setEmail(set.getString(index++));
        user.setPhone(set.getString(index++));
        user.setPassword(set.getString(index++));
        return user;
    }
}
