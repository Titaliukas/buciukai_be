package com.buciukai_be.service;

import com.buciukai_be.config.SystemSettingNames;
import com.buciukai_be.model.User;
import com.buciukai_be.model.UserRole;
import com.buciukai_be.repository.SystemSettingRepository;
import com.buciukai_be.repository.UserRepository;
import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AdminSystemService {

    private final SystemSettingRepository systemSettingRepository;
    private final UserRepository userRepository;

    private void assertAdmin(FirebaseToken token) {
        User user = userRepository
                .getUserByFirebaseUid(token.getUid())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (user.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    // Enable / disable registration
    public void setRegistrationEnabled(FirebaseToken token, boolean enabled) {
        assertAdmin(token);
        systemSettingRepository.updateActive(
                SystemSettingNames.REGISTRATION_ENABLED,
                enabled
        );
    }

    // System shutdown / activate
    public void setSystemActive(FirebaseToken token, boolean active) {
        assertAdmin(token);
        systemSettingRepository.updateActive(
                SystemSettingNames.SYSTEM_ACTIVE,
                active
        );
    }

    // Backup (logical trigger only)
    public void createBackup(FirebaseToken token) {
        assertAdmin(token);
        // intentionally empty or logged
        // backup trigger can be simulated
    }
}
