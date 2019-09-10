package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShipServiceImpl implements ShipService {

    private  ShipRepository shipRepository;
    @Autowired
    public ShipServiceImpl(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }


    @Override
    public List<Ship> getShipList(Map<String,Object> filters) {
        Iterable<Ship> shipIterable = shipRepository.findAll();
        List<Ship> shipList = new ArrayList<>();
        shipIterable.forEach(shipList::add);
        List<Ship> resultList = filterList(shipList,filters);;

        int pageNumber = (int) filters.get("pageNumber");
        int pageSize = (int) filters.get("pageSize");

        int from = Math.max(0,pageNumber*pageSize);
        int to = Math.min(resultList.size(),(pageNumber+1)*pageSize);

        shipList.clear();
        shipList= resultList.subList(from,to);

        return shipList;
    }

    private List<Ship> filterList(List<Ship> listToFilter, Map<String,Object> filter){
        List<Ship> shipList = new ArrayList<>(listToFilter);
        if (filter.get("name")!=null) {
            shipList = shipList.stream().filter(s -> s.name.contains((String)filter.get("name"))).collect(Collectors.toList());
        }
        if (filter.get("planet")!=null) {
            shipList = shipList.stream().filter(s -> s.planet.contains((String)filter.get("planet"))).collect(Collectors.toList());
        }
        if (filter.get("shipType")!=null) {
            shipList = shipList.stream().filter(s -> s.shipType.equals((ShipType)filter.get("shipType"))).collect(Collectors.toList());
        }
        if (filter.get("after")!=null) {
            shipList = shipList.stream().filter(s -> s.prodDate.getTime()>=(long) filter.get("after")).collect(Collectors.toList());
        }
        if (filter.get("before")!=null) {
            shipList = shipList.stream().filter(s -> s.prodDate.getTime()<=(long) filter.get("before")).collect(Collectors.toList());
        }
        if (filter.get("isUsed")!=null) {
            shipList = shipList.stream().filter(s -> s.isUsed.equals(filter.get("isUsed"))).collect(Collectors.toList());
        }
        if (filter.get("minSpeed")!=null) {
            shipList = shipList.stream().filter(s -> s.speed>=(double) filter.get("minSpeed")).collect(Collectors.toList());
        }
        if (filter.get("maxSpeed")!=null) {
            shipList = shipList.stream().filter(s -> s.speed<=(double) filter.get("maxSpeed")).collect(Collectors.toList());
        }
        if (filter.get("minCrewSize")!=null) {
            shipList = shipList.stream().filter(s -> s.crewSize>=(int) filter.get("minCrewSize")).collect(Collectors.toList());
        }
        if (filter.get("maxCrewSize")!=null) {
            shipList = shipList.stream().filter(s -> s.crewSize<=(int) filter.get("maxCrewSize")).collect(Collectors.toList());
        }
        if (filter.get("minRating")!=null) {
            shipList = shipList.stream().filter(s -> s.rating>=(double) filter.get("minRating")).collect(Collectors.toList());
        }
        if (filter.get("maxRating")!=null) {
            shipList = shipList.stream().filter(s -> s.rating<=(double) filter.get("maxRating")).collect(Collectors.toList());
        }
        if (filter.get("order")!=null) {
            switch ((ShipOrder)filter.get("order")) {
                case ID: shipList = shipList.stream().sorted((o1, o2) -> Long.compare(o1.id,o2.id)).collect(Collectors.toList()); break;
                case SPEED: shipList = shipList.stream().sorted((o1, o2) -> Double.compare(o1.speed,o2.speed)).collect(Collectors.toList()); break;
                case DATE: shipList = shipList.stream().sorted((o1, o2) -> Long.compare(o1.prodDate.getTime(),o2.prodDate.getTime())).collect(Collectors.toList()); break;
                case RATING: shipList = shipList.stream().sorted((o1, o2) -> Double.compare(o1.rating,o2.rating)).collect(Collectors.toList()); break;
            }
        }
        return shipList;
    }

    @Override
    public int getShipsCount(Map<String,Object> filters) {
        Iterable<Ship> shipIterable = shipRepository.findAll();
        List<Ship> shipList = new ArrayList<>();
        shipIterable.forEach(shipList::add);
        List<Ship> resultList = filterList(shipList,filters);;
        return resultList.size();
    }

    @Override
    public Ship createShip(Ship ship) {
        ship.rating=getRating(ship);
        Ship ship1= shipRepository.save(ship);
        return ship1;
    }

    @Override
    public Ship getShipById(Long id) {
        return shipRepository.findById(id).orElse(null);
    }

    @Override
    public Ship updateShip(Ship ship) {

        Ship ship1;
        if (shipRepository.existsById(ship.id)){
            ship1 = getShipById(ship.id);
            ship1.updateShip(ship);
            ship1.rating=getRating(ship1);
            shipRepository.save(ship1);
            return ship1;
        } else return null;
    }

    @Override
    public void deleteShipById(Long id) {
        shipRepository.deleteById(id);
    }

    private Date correctDate(Date date){
        Date date2 = new Date();
        date2.setYear(1970);
        date2.setMonth(Calendar.JANUARY);
        date2.setDate(1);
        return new Date(date.getTime()+date2.getTime());
    }

    private double getRating(Ship ship){
        Date date = correctDate(ship.prodDate);
        return Math.round(((80*ship.speed*(ship.isUsed? 0.5 : 1))/(3019-(date.getYear())+1))*100)/100d;
    }
}
