package com.buciukai_be.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyOccupancyReportDto {
    private LocalDate reportDate;
    private Integer hotelId;
    private String hotelName;
    private String hotelAddress;
    private String hotelCity;
    private int totalRooms;
    private int occupiedRooms;
    private List<OccupiedRoomDto> occupiedRoomsList;
}