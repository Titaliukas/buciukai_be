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
public class RoomStatusReportDto {
    private LocalDate checkDate;
    private Integer hotelId;
    private String hotelName;
    private String hotelAddress;
    private String hotelCity;
    private String statusFilter; // "reserved" or "free"
    private Integer totalRooms;
    private Integer filteredRoomsCount;
    private List<RoomAvailabilityDto> roomsList;
}