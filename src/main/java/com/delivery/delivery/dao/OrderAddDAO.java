package com.delivery.delivery.dao;

import com.delivery.delivery.model.OrderAdd;

public interface OrderAddDAO {

    OrderAdd insertAdd(OrderAdd add, int orderId);

    OrderAdd getByOrderId(int id);

    void updateAdd(OrderAdd add, int orderId);

    void deleteAdd(int orderId);

}
