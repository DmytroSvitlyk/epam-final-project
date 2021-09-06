package com.delivery.delivery.dao;

import com.delivery.delivery.model.Depot;

import java.util.List;

public interface DepotDAO {

    Depot insertDepot(Depot depot);

    Depot getById(int id);

    void updateDepot(Depot user);

    void deleteDepot(Depot user);

    List<Depot> getAllDepots();

}
