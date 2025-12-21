package com.buciukai_be.service;

import com.buciukai_be.api.dto.RoomDto;
import com.buciukai_be.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public List<RoomDto> getRoomsByHotelId(Integer hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    public RoomDto getRoomById(Integer id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    // DTO is now populated directly in repository via JOINs
}
