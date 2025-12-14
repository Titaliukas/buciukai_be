package com.buciukai_be.model;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {
    private Integer id;
    private String title;
    private String message;
    private LocalDate visibleUntil;
    private UUID adminId;
}
