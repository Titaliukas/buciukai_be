package com.buciukai_be.api.controller.admin;

import com.buciukai_be.model.BedType;
import com.buciukai_be.repository.BedTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminRoomMetaController {

    private final BedTypeRepository bedTypeRepository;


    @GetMapping("/bed-types")
    public List<BedType> getBedTypes() {
        return bedTypeRepository.findAll();
    }
}
