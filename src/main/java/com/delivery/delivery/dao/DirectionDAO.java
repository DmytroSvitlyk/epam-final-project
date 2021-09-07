package com.delivery.delivery.dao;

import com.delivery.delivery.model.Direction;

import java.util.List;

public interface DirectionDAO {

    Direction insertDirection(Direction direction);

    Direction getById(int id);

    List<Integer> getIdByCityLikeStatement(String depotFromLike, String depotToLike);

    void updateDirection(Direction direction);

    void deleteDirection(Direction direction);

    List<Direction> getAllDirections();

}
