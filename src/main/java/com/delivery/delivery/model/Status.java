package com.delivery.delivery.model;

public enum Status {
    PENDING(1), REJECTED(2), ACCEPTED(3), PAID(4);

    private int id;
    private String status;
    Status(int id) {
        this.id = id;
    }

    public static Status getStatus(int id) {
        for(Status s : values()) {
            if(s.id == id) {
                return s;
            }
        }
        return null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }
}
