package com.delivery.delivery.model;

import java.time.LocalTime;

public class Direction {

    private int id;
    private Depot depotFrom;
    private Depot depotTo;
    private LocalTime departureTime;
    private LocalTime arriveTime;
    private double range;

    public Direction(){
    }

    public Direction(int id, Depot depotFrom, Depot depotTo, LocalTime departureTime, LocalTime arriveTime, double range) {
        this.id = id;
        this.depotFrom = depotFrom;
        this.depotTo = depotTo;
        this.departureTime = departureTime;
        this.arriveTime = arriveTime;
        this.range = range;
    }

    public Direction(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Depot getDepotFrom() {
        return depotFrom;
    }

    public void setDepotFrom(Depot depotFrom) {
        this.depotFrom = depotFrom;
    }

    public Depot getDepotTo() {
        return depotTo;
    }

    public void setDepotTo(Depot depotTo) {
        this.depotTo = depotTo;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(LocalTime arriveTime) {
        this.arriveTime = arriveTime;
    }

}
