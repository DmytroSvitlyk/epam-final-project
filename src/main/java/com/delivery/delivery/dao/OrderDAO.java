package com.delivery.delivery.dao;

import com.delivery.delivery.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderDAO {

    Order insertOrder(Order order);

    Order getById(int id);

    List<Order> getByUserId(int id);

    List<Order> getByDirectionId(int id);

    List<Order> getByStatusId(int id);

    List<Order> getByGoalDate(LocalDate date);

    void updateStatus(Order order);

    void updateOrder(Order order);

    void deleteOrder(Order order);

}
