package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.HttpStatus.*;


@RestController
public class MainController {

    private ShipService shipService;

    @Autowired
    public void setShipService(ShipService shipService) {
        this.shipService = shipService;
    }

    @RequestMapping(value = "/rest/ships", method = RequestMethod.GET)
    public ResponseEntity<List<Ship>> getAllShips(
            @RequestParam(value = "name", required = false) Optional<String> name,
            @RequestParam(value = "planet", required = false) Optional<String> planet,
            @RequestParam(value = "shipType", required = false) Optional<ShipType> shipType,
            @RequestParam(value = "after", required = false) Optional<Long> after,
            @RequestParam(value = "before", required = false) Optional<Long> before,
            @RequestParam(value = "isUsed", required = false) Optional<Boolean> isUsed,
            @RequestParam(value = "minSpeed", required = false) Optional<Double> minSpeed,
            @RequestParam(value = "maxSpeed", required = false) Optional<Double> maxSpeed,
            @RequestParam(value = "minCrewSize", required = false) Optional<Integer> minCrewSize,
            @RequestParam(value = "maxCrewSize", required = false) Optional<Integer> maxCrewSize,
            @RequestParam(value = "minRating", required = false) Optional<Double> minRating,
            @RequestParam(value = "maxRating", required = false) Optional<Double> maxRating,
            @RequestParam(value = "order", required = false) Optional<ShipOrder> order,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize
    ) {
        Map<String, Object> filters = new HashMap<>();
        if (name.isPresent()) filters.put("name", name.get());
        if (planet.isPresent()) filters.put("planet", planet.get());
        if (shipType.isPresent()) filters.put("shipType", shipType.get());
        if (after.isPresent()) filters.put("after", after.get());
        if (before.isPresent()) filters.put("before", before.get());
        if (isUsed.isPresent()) filters.put("isUsed", isUsed.get());
        if (minSpeed.isPresent()) filters.put("minSpeed", minSpeed.get());
        if (maxSpeed.isPresent()) filters.put("maxSpeed", maxSpeed.get());
        if (minCrewSize.isPresent()) filters.put("minCrewSize", minCrewSize.get());
        if (maxCrewSize.isPresent()) filters.put("maxCrewSize", maxCrewSize.get());
        if (minRating.isPresent()) filters.put("minRating", minRating.get());
        if (maxRating.isPresent()) filters.put("maxRating", maxRating.get());
        if (order.isPresent()) filters.put("order", order.get());
        filters.put("pageNumber", pageNumber);
        filters.put("pageSize", pageSize);
        List<Ship> shipList = shipService.getShipList(filters);
        return ResponseEntity.ok(shipList);
    }

    @RequestMapping(value = "/rest/ships/count", method = RequestMethod.GET)
    public ResponseEntity<Integer> getShipCount(
            @RequestParam(value = "name", required = false) Optional<String> name,
            @RequestParam(value = "planet", required = false) Optional<String> planet,
            @RequestParam(value = "shipType", required = false) Optional<ShipType> shipType,
            @RequestParam(value = "after", required = false) Optional<Long> after,
            @RequestParam(value = "before", required = false) Optional<Long> before,
            @RequestParam(value = "isUsed", required = false) Optional<Boolean> isUsed,
            @RequestParam(value = "minSpeed", required = false) Optional<Double> minSpeed,
            @RequestParam(value = "maxSpeed", required = false) Optional<Double> maxSpeed,
            @RequestParam(value = "minCrewSize", required = false) Optional<Integer> minCrewSize,
            @RequestParam(value = "maxCrewSize", required = false) Optional<Integer> maxCrewSize,
            @RequestParam(value = "minRating", required = false) Optional<Double> minRating,
            @RequestParam(value = "maxRating", required = false) Optional<Double> maxRating
    ) {
        Map<String, Object> filters = new HashMap<>();
        if (name.isPresent()) filters.put("name", name.get());
        if (planet.isPresent()) filters.put("planet", planet.get());
        if (shipType.isPresent()) filters.put("shipType", shipType.get());
        if (after.isPresent()) filters.put("after", after.get());
        if (before.isPresent()) filters.put("before", before.get());
        if (isUsed.isPresent()) filters.put("isUsed", isUsed.get());
        if (minSpeed.isPresent()) filters.put("minSpeed", minSpeed.get());
        if (maxSpeed.isPresent()) filters.put("maxSpeed", maxSpeed.get());
        if (minCrewSize.isPresent()) filters.put("minCrewSize", minCrewSize.get());
        if (maxCrewSize.isPresent()) filters.put("maxCrewSize", maxCrewSize.get());
        if (minRating.isPresent()) filters.put("minRating", minRating.get());
        if (maxRating.isPresent()) filters.put("maxRating", maxRating.get());
        Integer count = shipService.getShipsCount(filters);
        return ResponseEntity.ok(count);
    }

    private Date correctDate(Date date) {
        Date date2 = new Date();
        date2.setYear(1970);
        date2.setMonth(Calendar.JANUARY);
        date2.setDate(1);
        return new Date(date.getTime() + date2.getTime());
    }

    @RequestMapping(value = "/rest/ships", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship) {
        ship.isUsed = ship.isUsed == null ? false : ship.isUsed;
        if (checkNull(ship) || validateShip(ship)) {
            return ResponseEntity.status(BAD_REQUEST).body(null);
        }
        Ship ship1 = shipService.createShip(ship);
        return ResponseEntity.ok(ship1);
    }

    @RequestMapping(value = "/rest/ships/{id}", method = RequestMethod.GET)
    public ResponseEntity<Ship> getShipById(@PathVariable("id") Long id) {
        if (id <= 0) return ResponseEntity.status(BAD_REQUEST).body(null);
        Ship ship = shipService.getShipById(id);
        if (ship == null) return ResponseEntity.status(NOT_FOUND).body(null);
        return ResponseEntity.ok(ship);
    }

    @RequestMapping(value = "/rest/ships/{id}", method = RequestMethod.POST)
    public ResponseEntity<Ship> updateShipById(@PathVariable("id") Long id, @RequestBody Ship ship) {
        if (id <= 0) return ResponseEntity.status(BAD_REQUEST).body(null);
        ship.id = id;
        Ship updatetedShip = shipService.updateShip(ship);
        if (updatetedShip == null) return ResponseEntity.status(NOT_FOUND).body(null);
        if (validateShip(updatetedShip)) {
            return ResponseEntity.status(BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(updatetedShip);
    }

    @RequestMapping(value = "/rest/ships/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteShipById(@PathVariable("id") Long id) {
        if (id <= 0) return ResponseEntity.status(BAD_REQUEST).body(null);
        Ship ship = shipService.getShipById(id);
        if (ship == null) return ResponseEntity.status(NOT_FOUND).body(null);
        shipService.deleteShipById(id);
        return ResponseEntity.ok(null);
    }

    private boolean validateShip(Ship ship) {
        Date date = correctDate(ship.prodDate);
        return ship.name.length() > 50 || ship.planet.length() > 50
                || ship.speed >= 1 || ship.speed <= 0
                || ship.crewSize >= 10000 || ship.crewSize <= 0
                || ship.name.equals("") || ship.planet.equals("")
                || date.getYear() >= 3020 || date.getYear() < 2800;
    }

    private boolean checkNull(Ship ship) {
        return ship == null || ship.speed == null || ship.name == null || ship.planet == null || ship.crewSize == null;
    }
}
