package com.buciukai_be.api.dto;

import com.buciukai_be.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserSignUpDto {
    private String firebaseUid;
    private String username;
    private String name;
    private String surname;
    private String email;
    private int role;
}
