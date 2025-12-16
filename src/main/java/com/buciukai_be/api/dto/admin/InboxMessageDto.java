package com.buciukai_be.api.dto.admin;

import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class InboxMessageDto {
    private Integer inboxId;

    private Integer announcementId;
    private String title;
    private String message;
    private LocalDate visibleUntil;

    private boolean isRead;
    private OffsetDateTime receivedAt;

    private UUID adminId;
}
