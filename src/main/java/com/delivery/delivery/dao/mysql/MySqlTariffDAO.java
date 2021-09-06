package com.delivery.delivery.dao.mysql;

import com.delivery.delivery.dao.TariffDAO;
import com.delivery.delivery.dao.pool.ConnectionPool;
import com.delivery.delivery.model.Tariff;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlTariffDAO implements TariffDAO {

    private static MySqlTariffDAO instance;

    private static final String GET_TARIFFS = "SELECT price FROM tariff";

    private MySqlTariffDAO() {
    }

    public static synchronized MySqlTariffDAO getInstance() {
        if(instance == null) {
            instance = new MySqlTariffDAO();
        }
        return instance;
    }

    @Override
    public void initTariff() {
        double[] tariffs = new double[12];
        try(Connection conn = ConnectionPool.getInstance().getConnection();
            ResultSet set = conn.createStatement().executeQuery(GET_TARIFFS)) {
            int i = 0;
            while(set.next()) {
                tariffs[i] = set.getDouble(2);
                i++;
            }
        } catch (SQLException e) {
            // TODO: 21.08.2021 ADD LOGGER
            e.printStackTrace();
        }
        Tariff.initTariff(tariffs);
    }
}
