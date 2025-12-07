package com.buciukai_be.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID id;
    private String firebaseUid;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private OffsetDateTime birthdate;
    private String city;
    private String postalCode;
    private UserRole Role;
    private OffsetDateTime createdAt;
}
