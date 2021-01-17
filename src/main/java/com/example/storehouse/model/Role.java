package com.example.storehouse.model;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
    USER(Set.of(Permission.DB_USERS_READ)),
    ADMIN(Set.of(Permission.DB_USERS_READ, Permission.DB_USERS_WRITE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    //Метод для конвертации Roles в SimpleGrantedAuthorities
    public Set<SimpleGrantedAuthority> getAuthorities(){
        return getPermissions().stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toSet());
    }
}
