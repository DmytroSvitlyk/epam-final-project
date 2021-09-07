package com.delivery.delivery.service;

import com.delivery.delivery.dao.DAOException;
import com.delivery.delivery.dao.DAOFactory;
import com.delivery.delivery.dao.DepotDAO;
import com.delivery.delivery.dao.DirectionDAO;
import com.delivery.delivery.model.Depot;
import com.delivery.delivery.model.Direction;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DirectionService {

    private static final Logger logger = Logger.getLogger(DepotService.class);

    private static DirectionService instance;

    private DirectionService() {
    }

    public static synchronized DirectionService getInstance() {
        if(instance == null) {
            instance = new DirectionService();
        }
        return instance;
    }

    private DirectionDAO getDirectionDAO() {
        return DAOFactory.getDAOFactory().getDirectionDAO();
    }

    private DepotDAO getDepotDAO() {
        return DAOFactory.getDAOFactory().getDepotDao();
    }

    public Direction getDirectionById(int id) {
        DirectionDAO directionDAO = getDirectionDAO();
        DepotDAO depotDAO = getDepotDAO();
        Direction direction = null;
        try {
            direction = directionDAO.getById(id);
            direction.setDepotFrom(depotDAO.getById(direction.getDepotFrom().getId()));
            direction.setDepotTo(depotDAO.getById(direction.getDepotTo().getId()));
        } catch (DAOException e) {
            logger.warn("Error while trying to get direction by id: " + id);
            throw new ServiceException(e);
        }
        return direction;
    }

    public Direction addDirection(Direction direction) {
        DirectionDAO dao = getDirectionDAO();
        try {
            return dao.insertDirection(direction);
        } catch (DAOException e) {
            logger.warn("Error while trying to add direction (depotFrom.id: " + direction.getDepotFrom().getId() + " depotTo.id: " + direction.getDepotTo().getId() + ")");
            throw new ServiceException(e);
        }
    }

    public void deleteDirection(Direction direction) {
        DirectionDAO dao = getDirectionDAO();
        try {
            dao.deleteDirection(direction);
        } catch (DAOException e) {
            logger.warn("Error while trying to delete direction (direction id: " + direction.getId());
            throw new ServiceException(e);
        }
    }

    public void updateDirection(Direction direction) {
        DirectionDAO dao = getDirectionDAO();
        try {
            dao.updateDirection(direction);
        } catch (DAOException e) {
            logger.warn("Error while trying to update direction (direction id: " + direction.getId());
            throw new ServiceException(e);
        }
    }

    public List<Direction> getDirectionsByDepotNames(String depotFrom, String depotTo) {
        DirectionDAO dao = getDirectionDAO();
        List<Direction> directions = new ArrayList<>();
        try {
            for(int id : dao.getIdByCityLikeStatement(depotFrom, depotTo)) {
                directions.add(dao.getById(id));
            }
        } catch (DAOException e) {
            logger.warn("Error while trying to get directions by statements: " + depotFrom + ", " + depotTo);
            throw new ServiceException(e);
        }
        return directions;
    }

}
