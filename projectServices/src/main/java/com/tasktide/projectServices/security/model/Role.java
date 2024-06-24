package com.tasktide.projectServices.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

import static com.tasktide.projectServices.security.model.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER(Set.of(USER_CREATE,
            USER_READ,
            USER_UPDATE,
            USER_DELETE)),

    ADMIN(Set.of(ADMIN_CREATE,
            ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_DELETE));
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new java.util.ArrayList<>(getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
