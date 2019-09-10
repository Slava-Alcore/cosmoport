package com.space.service;

import com.space.model.Ship;

import java.util.List;
import java.util.Map;

public interface ShipService {
    List<Ship> getShipList(Map<String,Object> filters) ;
    int getShipsCount(Map<String,Object> filters);
    Ship createShip (Ship ship);
    Ship getShipById (Long id);
    Ship updateShip (Ship ship);
    void deleteShipById(Long id);
}
