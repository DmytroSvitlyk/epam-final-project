package com.delivery.delivery.dao.mysql;

import com.delivery.delivery.dao.DAOException;
import com.delivery.delivery.dao.OrderDAO;
import com.delivery.delivery.dao.pool.ConnectionPool;
import com.delivery.delivery.model.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySqlOrderDAO implements OrderDAO {

    private static final Logger logger = Logger.getLogger(MySqlOrderDAO.class);

    private static MySqlOrderDAO instance;

    private static final String INSERT_ORDER = "INSERT INTO order_info (id, user_id, direction_id, status_id, price, from_address_need, to_address_need, uploading_need, unloading_need, address_from, address_to, goal_date, order_date, cargo_weight, cargo_length, cargo_width, cargo_height, cargo_volume)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String GET_ORDER_BY_ID = "SELECT * FROM order_info WHERE id = ?";
    private static final String GET_ALL = "SELECT * FROM order_info";
    private static final String GET_BY_GOAL_DATE = "SELECT * FROM order_info WHERE goal_date = ?";
    private static final String SET_STATUS = "UPDATE order_info SET status_id = ? WHERE id = ?";
    private static final String UPDATE_ORDER = "UPDATE order_info SET user_id = ?, direction_id = ?, status_id = ?, price = ?, from_address_need = ?, to_address_need = ?, uploading_need = ?, unloading_need = ?, address_from = ?, address_to = ?, goal_date = ?, order_date = ?, cargo_weight = ?, cargo_length = ?, cargo_width = ?, cargo_height = ?, cargo_volume = ? WHERE id = ?";
    private static final String DELETE_ORDER = "DELETE FROM order_info WHERE id = ?";
    private static final String GET_STATUS_BY_LOCALE = "SELECT osl.status_name FROM order_info " +
            "JOIN order_status os on order_info.status_id = os.id " +
            "JOIN order_status_loc osl on os.id = osl.status_id " +
            "JOIN language l on osl.lang_id = l.id WHERE order_info.id = ? AND lang_code = ?";

    private MySqlOrderDAO() {
    }

    public static synchronized MySqlOrderDAO getInstance() {
        if(instance == null) {
            instance = new MySqlOrderDAO();
        }
        return instance;
    }

    @Override
    public Order insertOrder(Order order) {
        try (Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)){
            int index = 1;
            statement.setInt(index++, order.getUser().getId());
            statement.setInt(index++, order.getDirection().getId());
            statement.setInt(index++, order.getStatus().getId());
            statement.setDouble(index++, order.getPrice());
            statement.setInt(index++, order.isFromAddressRequired() ? 1 : 0);
            statement.setInt(index++, order.isToAddressRequired() ? 1 : 0);
            statement.setInt(index++, order.isUploadingRequired() ? 1 : 0);
            statement.setInt(index++, order.isUnloadingRequired() ? 1 : 0);
            statement.setString(index++, order.getAdditional().getAddressFrom());
            statement.setString(index++, order.getAdditional().getAddressTo());
            statement.setDate(index++, Date.valueOf(order.getAdditional().getGoalDate()));
            statement.setDate(index++, Date.valueOf(order.getAdditional().getOrderDate()));
            statement.setDouble(index++, order.getCargo().getWeight());
            statement.setDouble(index++, order.getCargo().getLength());
            statement.setDouble(index++, order.getCargo().getWidth());
            statement.setDouble(index++, order.getCargo().getLength());
            statement.setDouble(index++, order.getCargo().getVolume());
            statement.execute();
            try(ResultSet set = statement.getGeneratedKeys()) {
                if(set.next()) {
                    order.setId(set.getInt(1));
                }
                else {
                    throw new SQLException("Unable to insert order to database");
                }
            }
        }
        catch (SQLException e) {
            logger.warn("Error during inserting order");
            throw new DAOException(e);

        }
        return order;
    }

    public String getStatusByLocale(int orderId, String langCode) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
         PreparedStatement statement = conn.prepareStatement(GET_STATUS_BY_LOCALE)) {
            statement.setInt(1, orderId);
            statement.setString(2, langCode);
            try(ResultSet set = statement.executeQuery()) {
                if(set.next()) {
                    return set.getString(1);
                }
                else {
                    throw new DAOException("No localization found for code: " + langCode);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    @Override
    public Order getById(int id) {
        Order order = null;
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(GET_ORDER_BY_ID)) {
            statement.setInt(1, id);
            try(ResultSet set = statement.executeQuery()) {
                if(set.next()) {
                    order = parseOrder(set);
                }
                else {
                    return null;
                }
            }
        }
        catch (SQLException e) {
            logger.warn("Unable to get order by id");
            throw new DAOException(e);
        }
        return order;
    }

    @Override
    public List<Order> getByUserId(int id) {
        return getByAttributeValue("user_id", id);
    }

    @Override
    public List<Order> getByDirectionId(int id) {
        return getByAttributeValue("direction_id", id);
    }

    @Override
    public List<Order> getByStatusId(int id) {
        return getByAttributeValue("status_id", id);
    }


    @Override
    public List<Order> getByGoalDate(LocalDate date) {
        List<Order> orders = new ArrayList<>();
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            ResultSet set = conn.createStatement().executeQuery(GET_BY_GOAL_DATE + date.toString())) {
            while(set.next()) {
                orders.add(parseOrder(set));
            }
        }
        catch (SQLException e) {
            logger.warn("Unable to get order IDs by goal date");
            throw new DAOException(e);
        }
        return orders;

    }

    @Override
    public void updateStatus(Order order) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(SET_STATUS)) {
            statement.setInt(1, order.getStatus().getId());
            statement.setInt(2, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Unable to set order status");
            throw new DAOException(e);
        }
    }

    @Override
    public void updateOrder(Order order) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(UPDATE_ORDER)) {
            int index = 1;
            statement.setInt(index++, order.getUser().getId());
            statement.setInt(index++, order.getDirection().getId());
            statement.setInt(index++, order.getStatus().getId());
            statement.setDouble(index++, order.getPrice());
            statement.setInt(index++, order.isFromAddressRequired() ? 1 : 0);
            statement.setInt(index++, order.isToAddressRequired() ? 1 : 0);
            statement.setInt(index++, order.isUploadingRequired() ? 1 : 0);
            statement.setInt(index++, order.isUnloadingRequired() ? 1 : 0);
            statement.setString(index++, order.getAdditional().getAddressFrom());
            statement.setString(index++, order.getAdditional().getAddressTo());
            statement.setDate(index++, Date.valueOf(order.getAdditional().getGoalDate()));
            statement.setDate(index++, Date.valueOf(order.getAdditional().getOrderDate()));
            statement.setDouble(index++, order.getCargo().getWeight());
            statement.setDouble(index++, order.getCargo().getLength());
            statement.setDouble(index++, order.getCargo().getWeight());
            statement.setDouble(index++, order.getCargo().getLength());
            statement.setDouble(index++, order.getCargo().getVolume());
            statement.setInt(index++, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Unable to update order");
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteOrder(Order order) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(DELETE_ORDER)) {
            statement.setInt(1, order.getId());
            statement.execute();
        } catch (SQLException e) {
            logger.warn("Unable to delete order");
            throw new DAOException(e);
        }
    }

    private Order parseOrder(ResultSet set) throws SQLException {
        Order order = new Order();
        OrderAdd additional = new OrderAdd();
        Cargo cargo = new Cargo();
        int index = 1;
        order.setId(set.getInt(index++));
        order.setUser(new User(set.getInt(index++)));
        order.setDirection(new Direction(set.getInt(index++)));
        order.setStatus(Status.getStatus(set.getInt(index++)));
        order.setPrice(set.getDouble(index++));
        order.setFromAddressRequired(set.getBoolean(index++));
        order.setToAddressRequired(set.getBoolean(index++));
        order.setUploadingRequired(set.getBoolean(index++));
        order.setUnloadingRequired(set.getBoolean(index++));
        additional.setAddressFrom(set.getString(index++));
        additional.setAddressTo(set.getString(index++));
        additional.setGoalDate(set.getDate(index++).toLocalDate());
        additional.setOrderDate(set.getDate(index++).toLocalDate());
        cargo.setWeight(set.getDouble(index++));
        cargo.setLength(set.getDouble(index++));
        cargo.setWidth(set.getDouble(index++));
        cargo.setHeight(set.getDouble(index++));
        cargo.setVolume(set.getDouble(index++));
        order.setAdditional(additional);
        order.setCargo(cargo);
        return order;
    }

    private List<Order> getByAttributeValue(String attribute, int value) {
        List<Order> orders = new ArrayList<>();
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            ResultSet set = conn.createStatement().executeQuery(GET_ALL + " WHERE " + attribute + " = " + value)) {
            while(set.next()) {
                orders.add(parseOrder(set));
            }
        }
        catch (SQLException e) {
            logger.warn("Unable to get orders by " + attribute + " = " + value);
            throw new DAOException(e);
        }
        return orders;
    }

}
