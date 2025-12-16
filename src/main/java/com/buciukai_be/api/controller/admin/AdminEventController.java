package com.buciukai_be.api.controller.admin;

import com.buciukai_be.model.Event;
import com.buciukai_be.service.AdminEventService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {

    private final AdminEventService adminEventService;

    @PostMapping
    public ResponseEntity<Event> createEvent(
            HttpServletRequest request,
            @RequestBody Event event
    ) {
        FirebaseToken firebaseUser =
                (FirebaseToken) request.getAttribute("firebaseUser");

        Event created =
                adminEventService.createEvent(firebaseUser, event);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
