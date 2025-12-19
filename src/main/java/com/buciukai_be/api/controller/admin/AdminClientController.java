package com.buciukai_be.api.controller.admin;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.buciukai_be.api.dto.admin.AdminUserDto;
import com.buciukai_be.api.dto.admin.AdminUserEmailDto;
import com.buciukai_be.api.dto.admin.AdminUserPasswordDto;
import com.buciukai_be.model.UserRole;
import com.buciukai_be.service.AdminClientService;
import com.google.firebase.auth.FirebaseToken;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

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
            adminClientService.getAllUsersNoAdmin(token)
    );
}


        @PatchMapping("/{userId}/role")
public ResponseEntity<Void> updateRole(
        HttpServletRequest request,
        @PathVariable UUID userId,
        @RequestBody Map<String, Integer> body
) {
    FirebaseToken token =
            (FirebaseToken) request.getAttribute("firebaseUser");

    if (token == null) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    Integer roleCode = body.get("role");
    if (roleCode == null) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Missing role"
        );
    }

    UserRole role = UserRole.fromCode(roleCode);

    adminClientService.updateRole(token, userId, role);

    return ResponseEntity.ok().build();
}


}
