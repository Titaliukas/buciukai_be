package com.buciukai_be.model;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInbox {
    private Integer id;
    private UUID userId;
    private Integer announcementId;
    private boolean isRead;
    private OffsetDateTime receivedAt;
}
