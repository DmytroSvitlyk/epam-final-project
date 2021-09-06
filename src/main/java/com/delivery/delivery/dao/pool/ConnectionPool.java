package com.delivery.delivery.dao.pool;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private static Logger logger = Logger.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;
    private static DataSource dataSource;

    private ConnectionPool() {
        Context initContext;
        try {
            initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/delivery");
        } catch (NamingException e) {
            logger.error("Can`t init data source");
            throw new PoolException(e);
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() throws PoolException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Can`t get connection from data source");
            throw new PoolException(e);
        }
    }

}
