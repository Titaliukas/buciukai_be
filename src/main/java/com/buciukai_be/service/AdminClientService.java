package com.buciukai_be.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.buciukai_be.api.dto.admin.AdminUserDto;
import com.buciukai_be.model.User;
import com.buciukai_be.model.UserRole;
import com.buciukai_be.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminClientService {

    private final UserRepository userRepository;

    /* =========================
       Security
       ========================= */
    private void assertAdmin(FirebaseToken token) {
        User admin = userRepository
                .getUserByFirebaseUid(token.getUid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (admin.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    /* =========================
       User listing
       ========================= */
    public List<AdminUserDto> getAllUsersNoAdmin(FirebaseToken token) {
    assertAdmin(token);

    return userRepository.getAllUsersNoAdmin().stream()
        .map(u -> {
            AdminUserDto dto = new AdminUserDto();
            dto.setId(u.getId());
            dto.setName(u.getName());
            dto.setSurname(u.getSurname());
            dto.setEmail(u.getEmail());
            dto.setCity(u.getCity());
            dto.setBirthdate(u.getBirthdate());
            dto.setBlocked(u.isBlocked());
            dto.setRole(UserRole.fromCode(u.getRole()));
            return dto;
        })
        .toList();
}


    /* =========================
       Blocking
       ========================= */
    public void blockUser(FirebaseToken token, UUID userId) {
    assertAdmin(token);

    User user = userRepository.getUserById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


    try {
        FirebaseAuth.getInstance().updateUser(
                new UserRecord.UpdateRequest(user.getFirebaseUid())
                        .setDisabled(true)
        );
    } catch (FirebaseAuthException e) {
        throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Failed to disable Firebase user"
        );
    }


    userRepository.blockUser(userId);
}


public void unblockUser(FirebaseToken token, UUID userId) {
    assertAdmin(token);

    User user = userRepository.getUserById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


    try {
        FirebaseAuth.getInstance().updateUser(
                new UserRecord.UpdateRequest(user.getFirebaseUid())
                        .setDisabled(false)
        );
    } catch (FirebaseAuthException e) {
        throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Failed to enable Firebase user"
        );
    }

    userRepository.unblockUser(userId);
}


    /* =========================
       Email change
       ========================= */
    public void changeEmail(FirebaseToken token, UUID userId, String newEmail) {
        assertAdmin(token);

        User user = userRepository.getUserById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        try {
            FirebaseAuth.getInstance().getUserByEmail(newEmail);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        } catch (FirebaseAuthException ignored) {}

        try {
            FirebaseAuth.getInstance().updateUser(
                    new UserRecord.UpdateRequest(user.getFirebaseUid())
                            .setEmail(newEmail)
            );
            userRepository.updateEmail(userId, newEmail);
        } catch (FirebaseAuthException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to update email");
        }
    }

    /* =========================
       Password change
       ========================= */
    public void changePasswordByUserId(
            FirebaseToken token,
            UUID userId,
            String newPassword
    ) {
        assertAdmin(token);

        User user = userRepository.getUserById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        try {
            FirebaseAuth.getInstance().updateUser(
                    new UserRecord.UpdateRequest(user.getFirebaseUid())
                            .setPassword(newPassword)
            );
        } catch (FirebaseAuthException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void updateRole(FirebaseToken token, UUID userId, UserRole role) {
    assertAdmin(token);
    userRepository.updateRole(userId, role.getCode());
}


}
