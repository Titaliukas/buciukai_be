package com.buciukai_be.api.controller.admin;

import com.buciukai_be.service.AdminSystemService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/system")
public class AdminSystemController {

    private final AdminSystemService adminSystemService;

    @PatchMapping("/registration")
    public ResponseEntity<Void> toggleRegistration(
            HttpServletRequest request,
            @RequestParam boolean enabled
    ) {
        FirebaseToken token =
                (FirebaseToken) request.getAttribute("firebaseUser");

        adminSystemService.setRegistrationEnabled(token, enabled);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/active")
    public ResponseEntity<Void> toggleSystem(
            HttpServletRequest request,
            @RequestParam boolean active
    ) {
        FirebaseToken token =
                (FirebaseToken) request.getAttribute("firebaseUser");

        adminSystemService.setSystemActive(token, active);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/backup")
    public ResponseEntity<Void> backup(HttpServletRequest request) {
        FirebaseToken token =
                (FirebaseToken) request.getAttribute("firebaseUser");

        adminSystemService.createBackup(token);
        return ResponseEntity.ok().build();
    }
}
