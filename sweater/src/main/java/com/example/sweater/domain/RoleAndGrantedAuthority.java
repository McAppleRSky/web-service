package com.example.sweater.domain;

import org.springframework.security.core.GrantedAuthority;

public enum RoleAndGrantedAuthority implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
