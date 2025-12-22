package com.buciukai_be.api.controller;

import com.buciukai_be.model.Hotel;
import com.buciukai_be.repository.HotelRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/hotels")
public class HotelController {
    private final HotelRepository hotelRepository;

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelRepository.getAllHotels();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Integer id) {
        Hotel hotel = hotelRepository.getHotelById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        return ResponseEntity.ok(hotel);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Integer starRating,
            @RequestParam(required = false) BigDecimal priceFrom,
            @RequestParam(required = false) BigDecimal priceTo,
            @RequestParam(required = false) Integer roomTypeId,
            @RequestParam(required = false) Integer bedTypeId,
            @RequestParam(required = false, defaultValue = "true") Boolean onlyAvailable,
            @RequestParam(required = false, defaultValue = "name") String sortBy

    ) {
        List<Hotel> hotels = hotelRepository.searchHotels(
                name,
                city,
                starRating,
                priceFrom,
                priceTo,
                roomTypeId,
                bedTypeId,
                onlyAvailable,
                sortBy
        );
        return ResponseEntity.ok(hotels);
    }

}