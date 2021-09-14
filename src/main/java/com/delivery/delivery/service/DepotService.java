package com.delivery.delivery.service;

import com.delivery.delivery.dao.DAOException;
import com.delivery.delivery.dao.DAOFactory;
import com.delivery.delivery.dao.DepotDAO;
import com.delivery.delivery.model.Depot;
import org.apache.log4j.Logger;

public class DepotService {

    private static final Logger logger = Logger.getLogger(DepotService.class);

    private static DepotService instance;

    private DepotService() {
    }

    public static synchronized DepotService getInstance() {
        if(instance == null) {
            instance = new DepotService();
        }
        return instance;
    }

    private DepotDAO getDepotDAO() {
        return DAOFactory.getDAOFactory().getDepotDao();
    }

    public Depot addDepot(Depot depot) {
        DepotDAO dao = getDepotDAO();
        try {
            return dao.insertDepot(depot);
        } catch (DAOException e) {
            logger.warn("Error while trying to add depot");
            throw new ServiceException(e);
        }
    }

    public int getIdByName(String name) {
        DepotDAO dao = getDepotDAO();
        try {
            return dao.getIdByName(name);
        } catch (DAOException e) {
            logger.warn("Error while trying to get depot by name");
            throw new ServiceException(e);
        }
    }

    public void deleteDepot(Depot depot) {
        DepotDAO dao = getDepotDAO();
        try {
            dao.deleteDepot(depot);
        } catch (DAOException e) {
            logger.warn("Unable to delete user (id : " + depot.getId() + " name : " + depot.getName() + ")");
            throw new ServiceException(e);
        }
    }

}
