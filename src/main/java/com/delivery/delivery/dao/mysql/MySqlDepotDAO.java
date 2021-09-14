package com.delivery.delivery.dao.mysql;

import com.delivery.delivery.dao.DAOException;
import com.delivery.delivery.dao.DepotDAO;
import com.delivery.delivery.dao.pool.ConnectionPool;
import com.delivery.delivery.model.Depot;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlDepotDAO implements DepotDAO {

    private static Logger logger = Logger.getLogger(MySqlDepotDAO.class);

    private static MySqlDepotDAO instance;

    private static final String INSERT_DEPOT = "INSERT INTO depot (depot_name, default_address)  VALUES (?, ?)";
    private static final String GET_BY_ID = "SELECT * FROM depot WHERE id = ?";
    private static final String GET_ALL = "SELECT * FROM depot";
    private static final String GET_BY_NAME = "SELECT id FROM depot WHERE depot_name = ?";
    private static final String UPDATE_DEPOT = "UPDATE depot SET depot_name = ?, default_address = ? WHERE id = ?";
    private static final String DELETE_DEPOT = "DELETE FROM depot WHERE id = ?";

    private MySqlDepotDAO() {
    }

    public static synchronized MySqlDepotDAO getInstance() {
        if(instance == null) {
            instance = new MySqlDepotDAO();
        }
        return instance;
    }

    @Override
    public Depot insertDepot(Depot depot) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(INSERT_DEPOT, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            statement.setString(index++, depot.getName());
            statement.setString(index++, depot.getAddress());
            statement.execute();
            try(ResultSet set = statement.getGeneratedKeys()) {
                if(set.next()) {
                    depot.setId(set.getInt(1));
                }
                else {
                    return null;
                }
            }
        }
        catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            logger.warn("Unable to add depot to database");
            throw new DAOException(e);
        }
        return depot;
    }

    @Override
    public Depot getById(int id) {
        Depot depot = null;
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(GET_BY_ID)) {
            statement.setInt(1, id);
            try(ResultSet set = statement.executeQuery()) {
                if(set.next()) {
                    depot = parseDepot(set);
                }
            }
        }
        catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
        return depot;
    }

    @Override
    public int getIdByName(String name) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(GET_BY_NAME)) {
            statement.setString(1, name);
            try(ResultSet set = statement.executeQuery()) {
                if(set.next()) {
                    return set.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.warn("Unable to find depot by name: " + name);
        }
        return -1;
    }


    @Override
    public void updateDepot(Depot depot) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(UPDATE_DEPOT)) {
            int index = 1;
            statement.setString(index++, depot.getName());
            statement.setString(index++, depot.getAddress());
            statement.setInt(index++, depot.getId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDepot(Depot depot) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(DELETE_DEPOT)) {
            statement.setInt(1, depot.getId());
            statement.execute();
        } catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
    }

    @Override
    public List<Depot> getAllDepots() {
        List<Depot> depots = new ArrayList<>();
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            ResultSet set = conn.createStatement().executeQuery(GET_ALL)) {
            while(set.next()) {
                depots.add(parseDepot(set));
            }
        } catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
        return depots;
    }

    private Depot parseDepot(ResultSet set) throws SQLException {
        return new Depot(set.getInt(1),
                set.getString(2),
                set.getString(3));
    }

}
