package com.delivery.delivery.dao;

import com.delivery.delivery.model.Order;
import com.delivery.delivery.model.Status;

import java.time.LocalDate;
import java.util.List;

public interface OrderDAO {

    Order insertOrder(Order order);

    Order getById(int id);

    List<Order> getByUserId(int id);

    List<Order> getByDirectionId(int id);

    List<Order> getByStatusId(int id);

    List<Integer> getIdByDate(int day, int month, int year);

    void updateStatus(Order order);

    void updateOrder(Order order);

    void deleteOrder(Order order);

}
