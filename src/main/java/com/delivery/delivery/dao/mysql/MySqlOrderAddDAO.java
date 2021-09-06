package com.delivery.delivery.dao.mysql;

import com.delivery.delivery.dao.OrderAddDAO;
import com.delivery.delivery.dao.pool.ConnectionPool;
import com.delivery.delivery.model.OrderAdd;

import java.sql.*;

public class MySqlOrderAddDAO implements OrderAddDAO {

    private static MySqlOrderAddDAO instance;

    private static final String INSERT_ADD = "INSERT INTO order_add (order_id, address_from, address_to, goal_date, order_date) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_BY_ORDER_ID = "SELECT * FROM order_add WHERE order_id = ?";
    private static final String UPDATE_ADD = "UPDATE order_add SET address_from = ?, address_to = ?, goal_date = ?, order_date = ? WHERE order_id = ?";
    private static final String DELETE_ADD = "DELETE FROM order_add WHERE order_id = ?";

    private MySqlOrderAddDAO() {
    }

    public static synchronized MySqlOrderAddDAO getInstance() {
        if(instance == null) {
            instance = new MySqlOrderAddDAO();
        }
        return instance;
    }

    @Override
    public OrderAdd insertAdd(OrderAdd add, int orderId) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(INSERT_ADD)) {
            int index = 1;
            statement.setInt(index++, orderId);
            statement.setString(index++, add.getAddressFrom());
            statement.setString(index++, add.getAddressTo());
            statement.setDate(index++, Date.valueOf(add.getGoalDate()));
            statement.setDate(index++, Date.valueOf(add.getOrderDate()));
            statement.execute();
        }
        catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
        return add;
    }

    @Override
    public OrderAdd getByOrderId(int id) {
        OrderAdd add = null;
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(GET_BY_ORDER_ID)) {
            statement.setInt(1, id);
            try(ResultSet set = statement.executeQuery()) {
                if(set.next()) {
                    add = parseAdd(set);
                }
                else {
                    throw new SQLException("Depot not found");
                }
            }
        }
        catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
        return add;
    }

    @Override
    public void updateAdd(OrderAdd add, int orderId) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(UPDATE_ADD)) {
            int index = 1;
            statement.setString(index++, add.getAddressFrom());
            statement.setString(index++, add.getAddressTo());
            statement.setDate(index++, Date.valueOf(add.getGoalDate()));
            statement.setDate(index++, Date.valueOf(add.getOrderDate()));
            statement.setInt(index++, orderId);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAdd(int orderId) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(DELETE_ADD)) {
            statement.setInt(1, orderId);
            statement.execute();
        } catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
    }


    private OrderAdd parseAdd(ResultSet set) throws SQLException {
        OrderAdd add = new OrderAdd();
        int index = 2;
        add.setAddressFrom(set.getString(index++));
        add.setAddressTo(set.getString(index++));
        add.setGoalDate(set.getDate(index++).toLocalDate());
        add.setOrderDate(set.getDate(index++).toLocalDate());
        return add;
    }

}
