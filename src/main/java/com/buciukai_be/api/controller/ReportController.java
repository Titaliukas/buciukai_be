package com.buciukai_be.api.controller;

import com.buciukai_be.api.dto.ClientHistoryReportDto;
import com.buciukai_be.api.dto.DailyOccupancyReportDto;
import com.buciukai_be.api.dto.HotelRevenueReportDto;
import com.buciukai_be.api.dto.RoomStatusReportDto;
import com.buciukai_be.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/occupancy/daily")
    public ResponseEntity<DailyOccupancyReportDto> getDailyOccupancyReport(
            @RequestParam Integer hotelId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        // Default to today if no date provided
        LocalDate reportDate = date != null ? date : LocalDate.now();

        DailyOccupancyReportDto report = reportService.getDailyOccupancyReport(hotelId, reportDate);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/client-history")
    public ResponseEntity<ClientHistoryReportDto> getClientHistory(@RequestParam String email) {
        ClientHistoryReportDto report = reportService.getClientHistory(email);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/room-status")
    public ResponseEntity<RoomStatusReportDto> getRoomStatusReport(
            @RequestParam Integer hotelId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam String status) {
        // Default to today if no date provided
        LocalDate checkDate = date != null ? date : LocalDate.now();

        RoomStatusReportDto report = reportService.getRoomStatusReport(hotelId, checkDate, status);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/hotel-revenue")
    public ResponseEntity<HotelRevenueReportDto> getHotelRevenueReport(
            @RequestParam Integer hotelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        try {
            HotelRevenueReportDto report = reportService.getHotelRevenueReport(hotelId, startDate, endDate);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}