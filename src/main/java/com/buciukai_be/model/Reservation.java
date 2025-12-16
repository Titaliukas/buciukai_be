package com.buciukai_be.model;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class Reservation {
    private Integer id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer status; // 1 confirmed, 2 cancelled, 3 completed
    private UUID clientId;
    private Integer roomId;
}
