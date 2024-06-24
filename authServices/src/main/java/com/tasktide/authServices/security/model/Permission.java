package com.tasktide.authServices.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {

    ADMIN_CREATE("adminCreate"),
    ADMIN_READ("adminRead"),
    ADMIN_UPDATE("adminUpdate"),
    ADMIN_DELETE("adminDelete"),

    USER_CREATE("userCreate"),
    USER_READ("userRead"),
    USER_UPDATE("userDelete"),
    USER_DELETE("userDelete");

    private final String permission;

}
