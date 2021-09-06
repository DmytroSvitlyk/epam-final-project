package com.delivery.delivery.dao.mysql;

import com.delivery.delivery.dao.*;

public class MySqlDAOFactory extends DAOFactory{

    private static MySqlDAOFactory instance;

    private MySqlDAOFactory() {
    }

    public static DAOFactory getInstance() {
        if(instance == null) {
            instance = new MySqlDAOFactory();
        }
        return instance;
    }

    @Override
    public UserDAO getUserDao() {
        return MySqlUserDAO.getInstance();
    }

    @Override
    public DepotDAO getDepotDao() {
        return MySqlDepotDAO.getInstance();
    }

    @Override
    public OrderDAO getOrderDao() {
        return MySqlOrderDAO.getInstance();
    }

    @Override
    public TariffDAO getTariffDao() {
        return MySqlTariffDAO.getInstance();
    }

    @Override
    public DirectionDAO getDirectionDAO() {
        return MySqlDirectionDAO.getInstance();
    }

    @Override
    public CargoDAO getCargoDAO() {
        return MySqlCargoDAO.getInstance();
    }

    @Override
    public OrderAddDAO getOrderAddDAO() {
        return MySqlOrderAddDAO.getInstance();
    }
}
