package com.delivery.delivery.dao.mysql;

import com.delivery.delivery.dao.DAOException;
import com.delivery.delivery.dao.DirectionDAO;
import com.delivery.delivery.dao.pool.ConnectionPool;
import com.delivery.delivery.model.Depot;
import com.delivery.delivery.model.Direction;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlDirectionDAO implements DirectionDAO {

    private static final Logger logger = Logger.getLogger(MySqlDirectionDAO.class);

    private static MySqlDirectionDAO instance;

    private static final String INSERT_DIRECTION = "INSERT INTO direction (depot_from_id, depot_to_id, departure_time, arrive_time , trip_range) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_BY_ID = "SELECT * FROM direction WHERE id = ?";
    private static final String GET_ALL = "SELECT * FROM direction LIMIT ";
    private static final String GET_ALL_COUNT = "SELECT COUNT(*) FROM direction";
    private static final String UPDATE_DIRECTION = "UPDATE direction SET depot_from_id = ?, depot_to_id = ?, departure_time = ?, arrive_time = ? , trip_range = ? WHERE id = ?";
    private static final String DELETE_DIRECTION = "DELETE FROM direction WHERE id = ?";
    private static final String SELECT_BY_LIKE = "SELECT di.id from direction di \n" +
                                            "JOIN depot d1 ON d1.id = di.depot_from_id \n" +
                                            "JOIN depot d2 ON d2.id = di.depot_to_id\n" +
                                            "WHERE d1.depot_name LIKE ? AND d2.depot_name LIKE ? ORDER BY %s %s LIMIT ?, ?;";

    private MySqlDirectionDAO() {
    }

    public static synchronized MySqlDirectionDAO getInstance() {
        if(instance == null) {
            instance = new MySqlDirectionDAO();
        }
        return instance;
    }

    @Override
    public Direction insertDirection(Direction direction) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(INSERT_DIRECTION, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            statement.setInt(index++, direction.getDepotFrom().getId());
            statement.setInt(index++, direction.getDepotTo().getId());
            statement.setTime(index++, Time.valueOf(direction.getDepartureTime()));
            statement.setTime(index++, Time.valueOf(direction.getArriveTime()));
            statement.setDouble(index++, direction.getRange());
            statement.execute();
            try(ResultSet set = statement.getGeneratedKeys()) {
                if(set.next()) {
                    direction.setId(set.getInt(1));
                }
                else {
                    throw new SQLException("Unable to insert depot to database");
                }
            }
        }
        catch (SQLException e) {
            logger.warn("Unable to add direction to database");
            throw new DAOException(e);
        }
        return direction;
    }

    @Override
    public Direction getById(int id) {
        Direction direction = null;
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(GET_BY_ID)) {
            statement.setInt(1, id);
            try(ResultSet set = statement.executeQuery()) {
                if(set.next()) {
                    direction = parseDirection(set);
                }
            }
        }
        catch (SQLException e) {
            logger.warn("Unable to find direction with id: " + id);
            throw new DAOException(e);
        }
        return direction;
    }

    @Override
    public List<Integer> getIdByCityLikeStatement(String depotFromLike, String depotToLike, String sortBy, int count, int page) {
        List<Integer> ids = new ArrayList<>();
        String order = "null";
        if(sortBy.equals("byFrom")) {
            order="d1.depot_name";
        }
        else if(sortBy.equals("byTo")) {
            order="d2.depot_name";
        }
        String sql = String.format(SELECT_BY_LIKE, order, "ASC");
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, depotFromLike + "%");
            statement.setString(2, depotToLike + "%");
            statement.setInt(3, count*page);
            statement.setInt(4, count);
            try(ResultSet set = statement.executeQuery()) {
                while(set.next()) {
                    ids.add(set.getInt(1));
                }
            }
        }
        catch (SQLException e) {
            logger.warn("Unable to find directions by like statements");
            throw new DAOException(e);
        }
        return ids;
    }

    @Override
    public void updateDirection(Direction direction) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(UPDATE_DIRECTION)) {
            int index = 1;
            statement.setInt(index++, direction.getDepotFrom().getId());
            statement.setInt(index++, direction.getDepotTo().getId());
            statement.setTime(index++, Time.valueOf(direction.getDepartureTime()));
            statement.setTime(index++, Time.valueOf(direction.getArriveTime()));
            statement.setDouble(index++, direction.getRange());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            logger.warn("Unable to update direction");
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteDirection(Direction direction) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(DELETE_DIRECTION)) {
            statement.setInt(1, direction.getId());
            statement.execute();
        } catch (SQLException e) {
            logger.warn("Unable to delete direction from database");
            throw new DAOException(e);
        }
    }

    @Override
    public List<Direction> getAllDirections(int count, int page) {
        List<Direction> directions = new ArrayList<>();
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            ResultSet set = conn.createStatement().executeQuery(GET_ALL + count*page + ", " + count)) {
            while(set.next()) {
                directions.add(parseDirection(set));
            }
        } catch (SQLException e) {
            logger.warn("Unable to get directions from database");
            throw new DAOException(e);
        }
        return directions;
    }

    @Override
    public int getPageCount(int onPageCount) {
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            ResultSet set = conn.createStatement().executeQuery(GET_ALL_COUNT)) {
            if(set.next()) {
                return (int)Math.ceil(set.getFloat(1)/onPageCount - 0.5);
            }
        } catch (SQLException e) {
            logger.warn("Unable to get directions from database");
            throw new DAOException(e);
        }
        return -1;
    }

    private Direction parseDirection(ResultSet set) throws SQLException {
        Direction direction = new Direction();
        int index = 1;
        direction.setId(set.getInt(index++));
        direction.setDepotFrom(new Depot(set.getInt(index++)));
        direction.setDepotTo(new Depot(set.getInt(index++)));
        direction.setDepartureTime(set.getTime(index++).toLocalTime());
        direction.setArriveTime(set.getTime(index++).toLocalTime());
        direction.setRange(set.getDouble(index++));
        return direction;
    }

}
