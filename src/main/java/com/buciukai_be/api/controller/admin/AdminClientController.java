package com.buciukai_be.api.controller.admin;

import com.buciukai_be.api.dto.admin.AdminUserDto;
import com.buciukai_be.api.dto.admin.AdminUserEmailDto;
import com.buciukai_be.api.dto.admin.AdminUserPasswordDto;
import com.buciukai_be.service.AdminClientService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/users")
public class AdminClientController {

    private final AdminClientService adminClientService;

    @PostMapping("/{userId}/block")
    public ResponseEntity<Void> blockUser(
            HttpServletRequest request,
            @PathVariable UUID userId
    ) {
        FirebaseToken token =
                (FirebaseToken) request.getAttribute("firebaseUser");

        adminClientService.blockUser(token, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/unblock")
    public ResponseEntity<Void> unblockUser(
            HttpServletRequest request,
            @PathVariable UUID userId
    ) {
        FirebaseToken token =
                (FirebaseToken) request.getAttribute("firebaseUser");

        adminClientService.unblockUser(token, userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}/email")
    public ResponseEntity<Void> changeEmail(
            HttpServletRequest request,
            @PathVariable UUID userId,
            @RequestBody AdminUserEmailDto dto
    ) {
        FirebaseToken token =
                (FirebaseToken) request.getAttribute("firebaseUser");

        adminClientService.changeEmail(token, userId, dto.getEmail());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}/password")
public ResponseEntity<Void> changePassword(
        HttpServletRequest request,
        @PathVariable UUID userId,
        @RequestBody AdminUserPasswordDto dto
) {
    FirebaseToken token =
            (FirebaseToken) request.getAttribute("firebaseUser");

    adminClientService.changePasswordByUserId(token, userId, dto.getPassword());
    return ResponseEntity.ok().build();
}


    @GetMapping
    public ResponseEntity<List<AdminUserDto>> getAllClients(HttpServletRequest request) {
    FirebaseToken token =
            (FirebaseToken) request.getAttribute("firebaseUser");

    return ResponseEntity.ok(
            adminClientService.getAllClients(token)
    );
}

}
