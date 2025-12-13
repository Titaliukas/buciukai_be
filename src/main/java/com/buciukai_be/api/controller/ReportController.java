package com.buciukai_be.api.controller;

import com.buciukai_be.api.dto.ClientHistoryReportDto;
import com.buciukai_be.api.dto.DailyOccupancyReportDto;
import com.buciukai_be.api.dto.HotelRevenueReportDto;
import com.buciukai_be.api.dto.ReportListDto;
import com.buciukai_be.api.dto.RoomStatusReportDto;
import com.buciukai_be.api.dto.SaveReportRequest;
import com.buciukai_be.service.ReportService;
import com.buciukai_be.service.UserService;
import com.google.firebase.auth.FirebaseToken;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;

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

    @PostMapping("/hotel-revenue/save")
    public ResponseEntity<Map<String, Object>> saveHotelRevenueReport(
            HttpServletRequest httpRequest,
            @RequestBody SaveReportRequest request,
            Authentication authentication) {

        try {
            FirebaseToken firebaseUser = (FirebaseToken) httpRequest.getAttribute("firebaseUser");
            String firebaseUid = firebaseUser.getUid();
            UUID userUuid = userService.getUuidByFirebaseUid(firebaseUid);

            Integer reportId = reportService.saveHotelRevenueReport(
                    request.getReportName(),
                    request.getStartDate(),
                    request.getEndDate(),
                    userUuid.toString());

            Map<String, Object> response = new HashMap<>();
            response.put("reportId", reportId);
            response.put("message", "Report saved successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<ReportListDto>> getAllReports() {
        try {
            List<ReportListDto> reports = reportService.getAllReports();
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}