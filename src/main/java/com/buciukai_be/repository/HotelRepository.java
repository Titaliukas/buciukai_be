package com.buciukai_be.repository;

import com.buciukai_be.model.Hotel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface HotelRepository {

    @Select("""
            SELECT
                id,
                name,
                address,
                city,
                country,
                postal_code as postalCode,
                phone_number as phoneNumber,
                email,
                star_rating as starRating,
                description,
                total_rooms as totalRooms
            FROM buciukai.hotel
            ORDER BY name
            """)
    List<Hotel> getAllHotels();

    @Select("""
            SELECT
                id,
                name,
                address,
                city,
                country,
                postal_code as postalCode,
                phone_number as phoneNumber,
                email,
                star_rating as starRating,
                description,
                total_rooms as totalRooms
            FROM buciukai.hotel
            WHERE id = #{id}
            """)
    Optional<Hotel> getHotelById(Integer id);
}