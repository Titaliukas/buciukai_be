package com.buciukai_be.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Event {
    private Integer id;
    private Integer hotelId;
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private UUID adminId;
}
