package com.buciukai_be.api.controller.admin;

import com.buciukai_be.model.Event;
import com.buciukai_be.service.AdminEventService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {

    private final AdminEventService adminEventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            HttpServletRequest request,
            @RequestBody Event event
    ) {
        FirebaseToken token =
                (FirebaseToken) request.getAttribute("firebaseUser");

        adminEventService.createEvent(token, event);
    }
}
