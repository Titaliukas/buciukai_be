package com.buciukai_be.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    CLIENT(1),
    STAFF(2),
    ADMIN(3);

    private final int code;

    public static UserRole fromCode(int code) {
        for (UserRole role : UserRole.values()) {
            if (role.getCode() == code) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role code: " + code);
    }

}
