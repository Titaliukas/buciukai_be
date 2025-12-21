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
                                h.id,
                                h.name,
                                h.address,
                                h.city,
                                h.country,
                                h.postal_code as postalCode,
                                h.phone_number as phoneNumber,
                                h.email,
                                h.star_rating as starRating,
                                h.description,
                                h.total_rooms as totalRooms,
                                (
                                    SELECT MIN(r.price)
                                    FROM buciukai.room r
                                    WHERE r.hotel_id = h.id
                                ) AS lowestPrice
                        FROM buciukai.hotel h
            ORDER BY name
            """)
    List<Hotel> getAllHotels();

    @Select("""
            SELECT
                                h.id,
                                h.name,
                                h.address,
                                h.city,
                                h.country,
                                h.postal_code as postalCode,
                                h.phone_number as phoneNumber,
                                h.email,
                                h.star_rating as starRating,
                                h.description,
                                h.total_rooms as totalRooms,
                                (
                                    SELECT MIN(r.price)
                                    FROM buciukai.room r
                                    WHERE r.hotel_id = h.id
                                ) AS lowestPrice
                        FROM buciukai.hotel h
                        WHERE h.id = #{id}
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