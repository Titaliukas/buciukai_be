package com.buciukai_be.service;

import com.buciukai_be.api.dto.RoomDto;
import com.buciukai_be.model.Room;
import com.buciukai_be.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public List<RoomDto> getRoomsByHotelId(Integer hotelId) {
        return roomRepository.findByHotelId(hotelId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public RoomDto getRoomById(Integer id) {
        return roomRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    private RoomDto convertToDto(Room room) {
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setPrice(room.getPrice());
        dto.setFloorNumber(room.getFloorNumber());
        dto.setIsAvailable(room.getIsAvailable());
        dto.setDescription(room.getDescription());
        dto.setSizeM2(room.getSizeM2());
        // roomType and bedType names not available without joins; leave null or map ids separately
        dto.setHotelId(room.getHotelId() != null ? room.getHotelId().longValue() : null);
        return dto;
    }
}
