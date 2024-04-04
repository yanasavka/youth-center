package com.example.youthcenter.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_USER, ROLE_MANAGER, ROLE_PARTICIPANT, ROLE_EMPLOYEE;

    @Override
    public String getAuthority() {
        return name();
    }
}
