package com.delivery.delivery.model;

public class Order {

    private int id;
    private User user;
    private Direction direction;
    private double price;
    private Cargo cargo;
    private OrderAdd additional;
    private Status status;
    private boolean fromAddressRequired = false;
    private boolean toAddressRequired = false;
    private boolean uploadingRequired = false;
    private boolean unloadingRequired = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public OrderAdd getAdditional() {
        return additional;
    }

    public void setAdditional(OrderAdd additional) {
        this.additional = additional;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isFromAddressRequired() {
        return fromAddressRequired;
    }

    public void setFromAddressRequired(boolean fromAddressRequired) {
        this.fromAddressRequired = fromAddressRequired;
    }

    public boolean isToAddressRequired() {
        return toAddressRequired;
    }

    public void setToAddressRequired(boolean toAddressRequired) {
        this.toAddressRequired = toAddressRequired;
    }

    public boolean isUploadingRequired() {
        return uploadingRequired;
    }

    public void setUploadingRequired(boolean uploadingRequired) {
        this.uploadingRequired = uploadingRequired;
    }

    public boolean isUnloadingRequired() {
        return unloadingRequired;
    }

    public void setUnloadingRequired(boolean unloadingRequired) {
        this.unloadingRequired = unloadingRequired;
    }

}
