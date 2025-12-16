package com.buciukai_be.api.controller.admin;

import com.buciukai_be.model.Hotel;
import com.buciukai_be.service.AdminHotelService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/hotels")
public class AdminHotelController {

    private final AdminHotelService adminHotelService;

    @PostMapping("/create")
    public ResponseEntity<Hotel> createHotel(
            HttpServletRequest request,
            @RequestBody Hotel hotel
    ) {
        FirebaseToken firebaseUser =
                (FirebaseToken) request.getAttribute("firebaseUser");

        Hotel created = adminHotelService.createHotel(firebaseUser, hotel);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
public ResponseEntity<List<Hotel>> getHotels(
        HttpServletRequest request
) {
    FirebaseToken firebaseUser =
            (FirebaseToken) request.getAttribute("firebaseUser");

    return ResponseEntity.ok(
            adminHotelService.getHotels(firebaseUser)
    );
}

}

