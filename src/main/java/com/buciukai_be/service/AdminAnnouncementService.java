package com.buciukai_be.service;

import com.buciukai_be.api.dto.admin.CreateAnnouncementDto;
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

    private User assertAdmin(FirebaseToken token) {
        User u = userRepository.getUserByFirebaseUid(token.getUid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not registered"));
        if (u.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "ADMIN role required");
        }
        return u;
    }

    public Announcement create(FirebaseToken token, CreateAnnouncementDto dto) {
        User admin = assertAdmin(token);

        Announcement a = Announcement.builder()
                .title(dto.getTitle())
                .message(dto.getMessage())
                .visibleUntil(dto.getVisibleUntil())
                .adminId(admin.getId())
                .build();

        announcementRepository.createAnnouncement(a);

        List<UUID> recipients = dto.getRecipientUserIds();
        if (recipients != null && !recipients.isEmpty()) {
            for (UUID userId : recipients) {
                UserInbox row = UserInbox.builder()
                        .userId(userId)
                        .announcementId(a.getId())
                        .isRead(false)
                        .build();
                userInboxRepository.createInboxRow(row);
            }
        }
        return a;
    }
}
