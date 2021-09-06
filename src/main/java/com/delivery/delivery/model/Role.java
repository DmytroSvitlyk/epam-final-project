package com.delivery.delivery.model;

public enum Role {
    COMMON(1), ADMIN(2), ANONYMOUS(3);

    private int id;
    Role(int id) {
        this.id = id;
    }

    public static Role getRole(int id) {
        for(Role role : values()) {
            if(role.id == id) {
                return role;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

}