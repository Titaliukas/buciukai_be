package com.buciukai_be.api.controller;

import com.buciukai_be.model.Announcement;
import com.buciukai_be.repository.AnnouncementRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/announcements")
public class AnnouncementController {

    private final AnnouncementRepository announcementRepository;

    @GetMapping("/news")
    public List<Announcement> getNews() {
        return announcementRepository.findActiveNews(LocalDate.now());
    }
}
