package com.example.storehouse.model;

public enum Permission {

    DB_USERS_READ("db:users:read"),
    DB_USERS_WRITE("db:users:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
