package com.delivery.delivery.dao.mysql;

import com.delivery.delivery.dao.DAOException;
import com.delivery.delivery.dao.OrderDAO;
import com.delivery.delivery.dao.pool.ConnectionPool;
import com.delivery.delivery.model.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlOrderDAO implements OrderDAO {

    Logger logger = Logger.getLogger(MySqlOrderDAO.class);
    private static MySqlOrderDAO instance;

    private static final String INSERT_ORDER = "INSERT INTO order_info (user_id, direction_id, status_id, price, from_address, to_address, uploading, unloading) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String INSERT_ORDER_ADD = "INSERT INTO order_add (order_id, address_from, address_to, goal_date, order_date) VALUES (?, ?, ?, ?, ?);";
    private static final String INSERT_ORDER_CARGO = "INSERT INTO order_cargo (order_id, weight, length, width, height, volume) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_ORDER_BY_ID = "SELECT * FROM order_info WHERE id = ?";
    private static final String GET_ALL = "SELECT * FROM order_info";
    private static final String GET_BY_DATE = "SELECT oi.id FROM order_info oi \n" +
                                            "JOIN order_add oa ON oi.id = oa.order_id\n" +
                                            "WHERE oa.goal_date = ";
    private static final String SET_STATUS = "UPDATE order_info SET status_id = ? WHERE id = ?";
    private static final String UPDATE_ORDER = "UPDATE order_info SET user_id = ?, direction_id = ?, status_id = ?, price = ?, from_address = ?, to_address = ?, uploading = ?, unloading = ? WHERE id = ?";
    private static final String DELETE_ORDER = "DELETE FROM order_info WHERE id = ?";
    private static final String GET_STATUS_BY_LOCALE = "SELECT osl.status_name FROM order_info\n" +
            "JOIN order_status os on order_info.status_id = os.id\n" +
            "JOIN order_status_loc osl on os.id = osl.status_id\n" +
            "JOIN language l on osl.lang_id = l.id WHERE order_info.id = ? AND lang_code = ?";

    private boolean enTried = false;

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
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            statement = conn.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            conn.setAutoCommit(false);
            int index = 1;
            statement.setInt(index++, order.getUser().getId());
            statement.setInt(index++, order.getDirection().getId());
            statement.setInt(index++, order.getStatus().getId());
            statement.setDouble(index++, order.getPrice());
            statement.setInt(index++, order.isFromAddressRequired() ? 1 : 0);
            statement.setInt(index++, order.isToAddressRequired() ? 1 : 0);
            statement.setInt(index++, order.isUploadingRequired() ? 1 : 0);
            statement.setInt(index++, order.isUnloadingRequired() ? 1 : 0);
            statement.execute();
            try(ResultSet set = statement.getGeneratedKeys()) {
                if(set.next()) {
                    order.setId(set.getInt(1));
                }
                else {
                    throw new SQLException("Unable to insert order to database");
                }
            }
            insertOrderADD(order.getAdditional(), order.getId(), conn, statement);
            insertOrderCargo(order.getCargo(), order.getId(), conn, statement);
            conn.commit();
        }
        catch (SQLException e1) {
            logger.warn("Error during inserting order. Rollback database...");
            try {
                conn.rollback();
            } catch (SQLException e2) {
                logger.warn("Error during rollback after unsuccessfully inserting order");
                throw new DAOException(e2);
            }
            logger.warn("Rollback performed successfully.");
        }
        finally {
            try {
                conn.setAutoCommit(true);
                statement.close();
                conn.close();
            } catch (SQLException e) {
                logger.warn("Error during closing statement and connection after inserting order.");
                throw new DAOException(e);
            }
        }
        return order;
    }

    private void insertOrderADD(OrderAdd add, int orderId, Connection conn, PreparedStatement statement) throws SQLException {
        Statement s = conn.prepareStatement(INSERT_ORDER_ADD);
        int index = 1;
        statement.setInt(index++, orderId);
        statement.setString(index++, add.getAddressFrom());
        statement.setString(index++, add.getAddressTo());
        statement.setDate(index++, Date.valueOf(add.getGoalDate()));
        statement.setDate(index++, Date.valueOf(add.getOrderDate()));
        statement.execute();
    }

    private void insertOrderCargo(Cargo cargo, int orderId, Connection conn, PreparedStatement statement) throws SQLException {
        statement = conn.prepareStatement(INSERT_ORDER_CARGO);
        int index = 1;
        statement.setInt(index++, orderId);
        statement.setDouble(index++, cargo.getWeight());
        statement.setDouble(index++, cargo.getLength());
        statement.setDouble(index++, cargo.getWidth());
        statement.setDouble(index++, cargo.getHeight());
        statement.setDouble(index++, cargo.getVolume());
        statement.execute();
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
                    throw new DAOException("No localization found for " + langCode);
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
                    throw new SQLException("Order not found");
                }
            }
        }
        catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public List<Order> getByUserId(int id) {
        return getByValueId("user_id", id);
    }

    @Override
    public List<Order> getByDirectionId(int id) {
        return getByValueId("direction_id", id);
    }

    @Override
    public List<Order> getByStatusId(int id) {
        return getByValueId("status_id", id);
    }


    @Override
    public List<Integer> getIdByDate(int day, int month, int year) {
        List<Integer> ids = new ArrayList<>();
        String goalDate = "'" + year + "-" + month + "-" + day + "'";
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            ResultSet set = conn.createStatement().executeQuery(GET_BY_DATE + goalDate)) {
            while(set.next()) {
                ids.add(set.getInt(1));
            }
        }
        catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
        return ids;

    }

    @Override
    public void updateStatus(Order order) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(SET_STATUS)) {
            statement.setInt(1, order.getStatus().getId());
            statement.setInt(2, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
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
            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrder(Order order) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(DELETE_ORDER)) {
            statement.setInt(1, order.getId());
            statement.execute();
        } catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
    }

    private Order parseOrder(ResultSet set) throws SQLException {
        Order order = new Order();
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
        return order;
    }

    private List<Order> getByValueId(String value, int id) {
        List<Order> orders = new ArrayList<>();
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            ResultSet set = conn.createStatement().executeQuery(GET_ALL + " WHERE " + value + " " + id)) {
            while(set.next()) {
                orders.add(parseOrder(set));
            }
        }
        catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
        return orders;
    }

}
