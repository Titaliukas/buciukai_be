package com.buciukai_be.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.buciukai_be.model.Hotel;

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

    @Insert("""
        INSERT INTO buciukai.hotel
        (name, address, city, country, postal_code, phone_number, email, star_rating, description, total_rooms)
        VALUES
        (#{name}, #{address}, #{city}, #{country}, #{postalCode}, #{phoneNumber}, #{email}, #{starRating}, #{description}, #{totalRooms})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void createHotel(Hotel hotel);

    @Select("""
    SELECT id, name
    FROM buciukai.hotel
""")
List<Hotel> findAll();

}