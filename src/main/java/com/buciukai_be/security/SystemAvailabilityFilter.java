package com.buciukai_be.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.buciukai_be.model.User;
import com.buciukai_be.model.UserRole;
import com.buciukai_be.repository.UserRepository;
import com.buciukai_be.service.AdminSystemService;
import com.google.firebase.auth.FirebaseToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SystemAvailabilityFilter extends OncePerRequestFilter {

    private final AdminSystemService systemSettingService;
    private final UserRepository userRepository;

    public static final String SYSTEM_ACTIVE = "SYSTEM_ACTIVE";
    public static final String REGISTRATION_ENABLED = "REGISTRATION_ENABLED";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        boolean systemActive =
                systemSettingService.isEnabled(SYSTEM_ACTIVE, true);

        boolean registrationEnabled =
                systemSettingService.isEnabled(REGISTRATION_ENABLED, true);

        FirebaseToken token =
                (FirebaseToken) request.getAttribute("firebaseUser");

        UserRole role = getUserRole(token);

        /* ===============================
           SYSTEM DISABLED LOGIC
           =============================== */
        if (!systemActive) {

            // Allow ONLY admins
            if (role != UserRole.ADMIN) {
                response.sendError(
                        HttpStatus.SERVICE_UNAVAILABLE.value(),
                        "Sistema laikinai išjungta"
                );
                return;
            }
        }

        /* ===============================
           REGISTRATION DISABLED LOGIC
           =============================== */
        if (!registrationEnabled) {
            if (path.contains("/users/signup")) {
                response.sendError(
                        HttpStatus.FORBIDDEN.value(),
                        "Registracija šiuo metu išjungta"
                );
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private UserRole getUserRole(FirebaseToken token) {
        if (token == null) {
            return null; // anonymous user
        }

        Optional<User> user =
                userRepository.getUserByFirebaseUid(token.getUid());

        return user.map(User::getRole).orElse(null);
    }

    /**
     * Do NOT block:
     * - Swagger
     * - Actuator
     * - Static content
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        return path.startsWith("/api/swagger")
                || path.startsWith("/api/v3/api-docs")
                || path.startsWith("/api/actuator")
                || path.startsWith("/error");
    }
}
