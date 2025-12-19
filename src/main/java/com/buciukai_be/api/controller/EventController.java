package com.buciukai_be.api.controller;

import com.buciukai_be.model.Event;
import com.buciukai_be.service.AdminEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EventController {

    private final AdminEventService eventService;

    @GetMapping("/hotel/{hotelId}")
    public List<Event> getHotelEvents(@PathVariable Integer hotelId) {
        return eventService.getHotelEvents(hotelId);
    }
}
