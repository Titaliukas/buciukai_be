package com.buciukai_be.api.dto;

import com.buciukai_be.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

    private String username;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private OffsetDateTime birthdate;
    private String city;
    private String postalCode;
}
