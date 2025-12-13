package com.buciukai_be.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientHistoryReportDto {
    private String clientId;
    private String clientName;
    private String clientSurname;
    private String clientEmail;
    private String clientPhone;
    private int totalReservations;
    private List<ClientReservationDto> reservations;
}