package com.delivery.delivery.model;

public class Depot {

    private int id;
    private String city;
    private String address;

    public Depot(int id) {
        this.id = id;
    }

    public Depot(int id, String name, String address) {
        this.id = id;
        this.city = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
