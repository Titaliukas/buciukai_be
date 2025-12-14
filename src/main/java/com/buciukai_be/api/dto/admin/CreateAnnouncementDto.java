package com.buciukai_be.api.dto.admin;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class CreateAnnouncementDto {

    private String title;
    private String message;
    private LocalDate visibleUntil;

    private List<UUID> recipientUserIds;
}
