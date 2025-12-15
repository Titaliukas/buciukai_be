package com.buciukai_be.api.dto.admin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.buciukai_be.model.AnnouncementType;

import lombok.Data;

@Data
public class CreateAnnouncementRequest {
    private String title;
    private String message;
    private LocalDate visibleUntil;
    private AnnouncementType type;     
    private List<UUID> recipients = new ArrayList<>();      
}

