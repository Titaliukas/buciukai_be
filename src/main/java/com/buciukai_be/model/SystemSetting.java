package com.buciukai_be.model;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemSetting {

    private UUID id;

    private String name;
    private boolean isActive;
    private String description;
}
