package com.buciukai_be.api.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buciukai_be.model.BedType;
import com.buciukai_be.model.RoomType;
import com.buciukai_be.repository.BedTypeRepository;
import com.buciukai_be.repository.RoomTypeRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminRoomMetaController {

    private final BedTypeRepository bedTypeRepository;
    private final RoomTypeRepository roomTypeRepository;

    @GetMapping("/bed-types")
    public List<BedType> getBedTypes() {
        return bedTypeRepository.findAll();
    }

    @GetMapping("/room-types")
    public List<RoomType> getRoomTypes() {
        return roomTypeRepository.findAll();
    }
}
