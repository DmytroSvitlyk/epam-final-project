package com.delivery.delivery.dao;

import com.delivery.delivery.model.User;

import java.util.List;

public interface UserDAO {

    User insertUser(User user);

    User getById(int id);

    User getByLogin(String login);

    boolean canRegisterUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    List<User> getAllUsers();

}
