package com.delivery.delivery.dao;

import com.delivery.delivery.dao.mysql.MySqlDAOFactory;

import java.util.ResourceBundle;

public abstract class DAOFactory {

    private static final String MYSQL = "MYSQL";
    private static final String dbType = ResourceBundle.getBundle("database").getString("db.type");

    public static DAOFactory getDAOFactory() {
        DAOFactory factory;
        switch (dbType) {
            case MYSQL:
                factory = MySqlDAOFactory.getInstance();
                break;
            default:
                throw new IllegalStateException("No such implementation for " + dbType + " DAO");
        }
        return factory;
    }

    public abstract UserDAO getUserDao();

    public abstract DepotDAO getDepotDao();

    public abstract OrderDAO getOrderDao();

    public abstract TariffDAO getTariffDao();

    public abstract DirectionDAO getDirectionDAO();

    public abstract CargoDAO getCargoDAO();

    public abstract OrderAddDAO getOrderAddDAO();

}
