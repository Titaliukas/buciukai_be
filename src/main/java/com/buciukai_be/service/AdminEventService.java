package com.buciukai_be.service;

import com.buciukai_be.model.Event;
import com.buciukai_be.model.User;
import com.buciukai_be.model.UserRole;
import com.buciukai_be.repository.EventRepository;
import com.buciukai_be.repository.UserRepository;
import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminEventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    private User assertAdmin(FirebaseToken token) {
        User user = userRepository
                .getUserByFirebaseUid(token.getUid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (user.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return user;
    }

    public void createEvent(FirebaseToken token, Event event) {
        User admin = assertAdmin(token);
        event.setAdminId(admin.getId());
        eventRepository.create(event);
    }

    public List<Event> getHotelEvents(Integer hotelId) {
        return eventRepository.findByHotelId(hotelId);
    }
}
