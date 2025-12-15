package com.buciukai_be.api.dto.admin;

import com.buciukai_be.model.AnnouncementType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class CreateAnnouncementRequest {
    private String title;
    private String message;
    private LocalDate visibleUntil;
    private AnnouncementType type;     
    private List<UUID> recipients;      
}

