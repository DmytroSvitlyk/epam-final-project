package com.delivery.delivery.dao;

import com.delivery.delivery.model.Direction;

import java.util.List;

public interface DirectionDAO {

    Direction insertDirection(Direction direction);

    Direction getById(int id);

    List<Integer> getIdByCityLikeStatement(String depotFromLike, String depotToLike, String sortBy, int count, int page);

    void updateDirection(Direction direction);

    void deleteDirection(Direction direction);

    List<Direction> getAllDirections(int count, int page);

    int getPageCount(int onPageCount);

}
