package com.buciukai_be.api.controller;

import com.buciukai_be.model.Hotel;
import com.buciukai_be.repository.HotelRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Integer id) {
        Hotel hotel = hotelRepository.getHotelById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        return ResponseEntity.ok(hotel);
    }
}