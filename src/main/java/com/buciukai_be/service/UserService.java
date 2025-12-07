package com.buciukai_be.service;

import com.buciukai_be.model.User;
import com.buciukai_be.model.UserRole;
import com.buciukai_be.repository.UserRepository;
import com.buciukai_be.api.dto.UserSignUpDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(UserSignUpDto dto) {
        // Check if user already exists
        userRepository.findByFirebaseUid(dto.getFirebaseUid())
                .ifPresent(u -> {
                    throw new RuntimeException("User already exists");
                });

        // Map DTO to entity
        User user = User.builder()
                .firebaseUid(dto.getFirebaseUid())
                .username(dto.getUsername())
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .Role(UserRole.fromCode(dto.getRole()))
                .build();


        // Save user
        userRepository.createUser(user);

        // Handle STAFF / CLIENT extra tables
//        switch (user.getRole()) {
//            case STAFF -> {
//                Staff staff = new Staff();
//                staff.setUser(user);
//                staff.setPosition("Unknown"); // default
//                staff.setSalary(BigDecimal.ZERO);
//                // save via staff repository
//            }
//            case CLIENT -> {
//                Client client = new Client();
//                client.setUser(user);
//                client.setTotalReservations(0);
//                // save via client repository
//            }
//        }

        return user;
    }
}
