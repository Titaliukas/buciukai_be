package com.buciukai_be.api.controller.admin;

import com.buciukai_be.api.dto.admin.CreateAnnouncementRequest;
import com.buciukai_be.model.Announcement;
import com.buciukai_be.service.AdminAnnouncementService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/announcements")
public class AdminAnnouncementController {

    private final AdminAnnouncementService service;

    @PostMapping
    public ResponseEntity<Void> create(
        HttpServletRequest request,
        @RequestBody CreateAnnouncementRequest req
    ) {
        FirebaseToken token =
            (FirebaseToken) request.getAttribute("firebaseUser");

        service.create(token, req);
        return ResponseEntity.ok().build();
    }
}

