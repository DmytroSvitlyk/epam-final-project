package com.delivery.delivery.dao.mysql;

import com.delivery.delivery.dao.DAOException;
import com.delivery.delivery.dao.UserDAO;
import com.delivery.delivery.model.Role;
import com.delivery.delivery.model.User;
import com.delivery.delivery.dao.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserDAO implements UserDAO {

    private static MySqlUserDAO instance;

    private static final String INSERT_USER = "INSERT INTO user (first_name, second_name, login, email, phone, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String GET_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
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
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
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
                    throw new SQLException("User not found");
                }
            }
        }
        catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
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
                    throw new DAOException("User not found");
                }
            }
        }
        catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
        return user;
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
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(User user) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(DELETE_USER)) {
            statement.setInt(1, user.getId());
            statement.execute();
        } catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
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
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
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
