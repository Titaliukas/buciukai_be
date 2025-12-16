package com.buciukai_be.api.dto.admin;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class AdminUserDto {
    private UUID id;
    private String firebaseUid;
    private String name;
    private String surname;
    private String email;
    private String city;
    private OffsetDateTime birthdate;
    private boolean isBlocked;
}
