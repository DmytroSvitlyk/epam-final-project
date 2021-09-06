package com.delivery.delivery.dao;

import com.delivery.delivery.model.Cargo;

public interface CargoDAO {

    Cargo insertCargo(Cargo cargo, int orderId);

    Cargo getByOrderId(int id);

    void updateCargo(Cargo cargo, int orderId);

    void deleteCargo(int orderId);

}
