package com.buciukai_be.service;

import com.buciukai_be.model.Hotel;
import com.buciukai_be.model.User;
import com.buciukai_be.model.UserRole;
import com.buciukai_be.repository.HotelRepository;
import com.buciukai_be.repository.UserRepository;
import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminHotelService {

    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    public Hotel createHotel(FirebaseToken firebaseUser, Hotel hotel) {

        System.out.println("🔥 HOTEL CREATE UID: " + firebaseUser.getUid());

    User user = userRepository
            .getUserByFirebaseUid(firebaseUser.getUid())
            .orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.UNAUTHORIZED)
            );

    if (user.getRole() != UserRole.ADMIN) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
    hotelRepository.createHotel(hotel);
    return hotel;
}

}
