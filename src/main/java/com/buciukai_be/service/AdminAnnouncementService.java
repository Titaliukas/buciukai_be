package com.buciukai_be.service;

import com.buciukai_be.api.dto.admin.CreateAnnouncementRequest;
import com.buciukai_be.model.AnnouncementType;
import com.buciukai_be.model.Announcement;
import com.buciukai_be.model.User;
import com.buciukai_be.model.UserInbox;
import com.buciukai_be.model.UserRole;
import com.buciukai_be.repository.AnnouncementRepository;
import com.buciukai_be.repository.UserInboxRepository;
import com.buciukai_be.repository.UserRepository;
import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AdminAnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final UserInboxRepository userInboxRepository;
    private final UserRepository userRepository;

    public void create(
        FirebaseToken token,
        CreateAnnouncementRequest req
    ) {
        User admin = userRepository
            .getUserByFirebaseUid(token.getUid())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (admin.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Announcement announcement = Announcement.builder()
            .title(req.getTitle())
            .message(req.getMessage())
            .visibleUntil(req.getVisibleUntil())
            .type(req.getType())
            .adminId(admin.getId())
            .build();

        Integer announcementId = announcementRepository.create(announcement);

        if (req.getType() == AnnouncementType.INBOX) {
            for (UUID userId : req.getRecipients()) {
                userInboxRepository.insert(userId, announcementId);
            }
        }
    }
}

