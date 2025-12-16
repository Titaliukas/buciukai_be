package com.buciukai_be.service;

import com.buciukai_be.api.dto.admin.AdminUserDto;
import com.buciukai_be.model.User;
import com.buciukai_be.model.UserRole;
import com.buciukai_be.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AdminClientService {

    private final UserRepository userRepository;

    private void assertAdmin(FirebaseToken token) {
        User admin = userRepository
                .getUserByFirebaseUid(token.getUid())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (admin.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    public void blockUser(FirebaseToken token, UUID userId) {
        assertAdmin(token);
        userRepository.blockUser(userId);
    }

    public void unblockUser(FirebaseToken token, UUID userId) {
        assertAdmin(token);
        userRepository.unblockUser(userId);
    }

    public void changeEmail(FirebaseToken token, UUID userId, String newEmail) {
    assertAdmin(token);

    User user = userRepository.getUserById(userId)
            .orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
            );

    // Check if email already exists in Firebase
    try {
        FirebaseAuth.getInstance().getUserByEmail(newEmail);
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Email already in use"
        );
    } catch (FirebaseAuthException ignored) {
        // email is free
    }

    try {
        FirebaseAuth.getInstance().updateUser(
                new UserRecord.UpdateRequest(user.getFirebaseUid())
                        .setEmail(newEmail)
        );

        userRepository.updateEmail(userId, newEmail);

    } catch (FirebaseAuthException e) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Failed to update email"
        );
    }
}


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

    public List<AdminUserDto> getAllClients(FirebaseToken token) {
    assertAdmin(token);
    return userRepository.getAllClients();
    }

    public void changePassword(FirebaseToken token, String firebaseUid, String newPassword) {
        assertAdmin(token);

        try {
            UserRecord.UpdateRequest request =
                    new UserRecord.UpdateRequest(firebaseUid)
                            .setPassword(newPassword);

            FirebaseAuth.getInstance().updateUser(request);

        } catch (FirebaseAuthException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Failed to update password"
            );
        }
    }
}
