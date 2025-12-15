package com.buciukai_be.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemSetting {

    private Integer id;

    private String name;
    private boolean isActive;
    private String description;
}