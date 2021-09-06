package com.delivery.delivery.dao.mysql;

import com.delivery.delivery.dao.CargoDAO;
import com.delivery.delivery.dao.pool.ConnectionPool;
import com.delivery.delivery.model.Cargo;

import java.sql.*;

public class MySqlCargoDAO implements CargoDAO {

    private static MySqlCargoDAO instance;

    private static final String INSERT_CARGO = "INSERT INTO order_cargo (order_id, weight, length, width, height, volume) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_BY_ORDER_ID = "SELECT * FROM order_cargo WHERE order_id = ?";
    private static final String UPDATE_CARGO = "UPDATE order_cargo SET weight = ?, length = ?, width = ?, height = ?, volume = ? WHERE order_id = ?";
    private static final String DELETE_CARGO = "DELETE FROM order_cargo WHERE order_id = ?";

    private MySqlCargoDAO() {
    }

    public static synchronized MySqlCargoDAO getInstance() {
        if(instance == null) {
            instance = new MySqlCargoDAO();
        }
        return instance;
    }

    @Override
    public Cargo insertCargo(Cargo cargo, int orderId) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(INSERT_CARGO)) {
            int index = 1;
            statement.setInt(index++, orderId);
            statement.setDouble(index++, cargo.getWeight());
            statement.setDouble(index++, cargo.getLength());
            statement.setDouble(index++, cargo.getWidth());
            statement.setDouble(index++, cargo.getHeight());
            statement.setDouble(index++, cargo.getVolume());
            statement.execute();
        }
        catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
        return cargo;
    }

    @Override
    public Cargo getByOrderId(int id) {
        Cargo cargo = null;
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(GET_BY_ORDER_ID)) {
            statement.setInt(1, id);
            try(ResultSet set = statement.executeQuery()) {
                if(set.next()) {
                    cargo = parseCargo(set);
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
        return cargo;
    }

    @Override
    public void updateCargo(Cargo cargo, int orderId) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(UPDATE_CARGO)) {
            int index = 1;
            statement.setDouble(index++, cargo.getWeight());
            statement.setDouble(index++, cargo.getLength());
            statement.setDouble(index++, cargo.getWidth());
            statement.setDouble(index++, cargo.getHeight());
            statement.setDouble(index++, cargo.getVolume());
            statement.setInt(index++, orderId);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCargo(int orderId) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(DELETE_CARGO)) {
            statement.setInt(1, orderId);
            statement.execute();
        } catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
    }

    private Cargo parseCargo(ResultSet set) throws SQLException {
        Cargo cargo = new Cargo();
        int index = 2;
        cargo.setWeight(set.getDouble(index++));
        cargo.setLength(set.getDouble(index++));
        cargo.setWidth(set.getDouble(index++));
        cargo.setHeight(set.getDouble(index++));
        cargo.setVolume(set.getDouble(index++));
        return cargo;
    }

}
