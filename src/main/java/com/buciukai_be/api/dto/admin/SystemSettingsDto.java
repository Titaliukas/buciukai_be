package com.buciukai_be.api.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SystemSettingsDto {
    private boolean systemActive;
    private boolean registrationEnabled;
}
