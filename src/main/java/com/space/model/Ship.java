package com.space.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Table(name = "ship",schema = "cosmoport")
@Entity

public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;
    @Column(name = "name")
    public String name;
    @Column(name = "planet")
    public String planet;
    @Column(name = "shipType")
    @Enumerated(EnumType.STRING)
    public ShipType shipType;
    @Column(name = "prodDate")
    public Date prodDate;
    @Column(name = "isUsed")
    @Type(type = "numeric_boolean")
    public Boolean isUsed;
    @Column(name = "speed")
    public Double speed;
    @Column(name = "crewSize")
    public Integer crewSize;
    @Column(name = "rating")
    public Double rating;


    public Ship(String name, String planet, ShipType shipType, Date prodDate, Boolean isUsed, Double speed, Integer crewSize) {
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.prodDate = prodDate;
        this.isUsed = isUsed;
        this.speed = speed;
        this.crewSize = crewSize;
        this.rating = null;
    }

    public Ship() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return id.equals(ship.id) &&
                name.equals(ship.name) &&
                planet.equals(ship.planet) &&
                shipType == ship.shipType &&
                prodDate.equals(ship.prodDate) &&
                isUsed.equals(ship.isUsed) &&
                speed.equals(ship.speed) &&
                crewSize.equals(ship.crewSize) &&
                rating.equals(ship.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, planet, shipType, prodDate, isUsed, speed, crewSize, rating);
    }

    public void updateShip (Ship ship){
        this.name = ship.name==null ? this.name : ship.name;
        this.planet = ship.planet==null ? this.planet : ship.planet;
        this.shipType = ship.shipType==null? this.shipType : ship.shipType;
        this.prodDate = ship.prodDate==null? this.prodDate : ship.prodDate;
        this.isUsed = ship.isUsed==null? this.isUsed : ship.isUsed;
        this.speed = ship.speed==null? this.speed : ship.speed;
        this.crewSize = ship.crewSize==null? this.crewSize : ship.crewSize;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + (id==null ? "empty" : id) +
                ", name='" + (name==null? "empty" : name) + '\'' +
                ", planet='" + (planet==null? "empty" : planet) + '\'' +
                ", shipType=" + (shipType==null? "empty" : shipType) +
                ", prodDate=" + (prodDate==null? "empty" : prodDate.getYear())+
                ", isUsed=" + (isUsed==null? "empty" : isUsed) +
                ", speed=" + (speed==null? "empty" : speed) +
                ", crewSize=" + (crewSize==null? "empty" : crewSize) +
                ", rating=" + (rating==null? "empty" : rating) +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
