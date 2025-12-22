package com.buciukai_be.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import org.apache.ibatis.annotations.Param;


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

@Select({
        "<script>",
        "SELECT",
        "  h.id,",
        "  h.name,",
        "  h.address,",
        "  h.city,",
        "  h.country,",
        "  h.postal_code  AS postalCode,",
        "  h.phone_number AS phoneNumber,",
        "  h.email,",
        "  h.star_rating  AS starRating,",
        "  h.description,",
        "  h.total_rooms  AS totalRooms,",
        "  MIN(r.price)   AS lowestPrice",
        "FROM buciukai.hotel h",
        "JOIN buciukai.room r ON r.hotel_id = h.id",
        "WHERE 1=1",
        "  <if test=\"name != null and name != ''\">",
        "    AND LOWER(h.name) LIKE CONCAT('%', LOWER(#{name}), '%')",
        "  </if>",
        "  <if test=\"city != null and city != ''\">",
        "    AND LOWER(h.city) LIKE CONCAT('%', LOWER(#{city}), '%')",
        "  </if>",
        "  <if test='starRating != null'>",
        "    AND h.star_rating = #{starRating}",
        "  </if>",
        "  <if test='priceFrom != null'>",
        "    AND r.price &gt;= #{priceFrom}",
        "  </if>",
        "  <if test='priceTo != null'>",
        "    AND r.price &lt;= #{priceTo}",
        "  </if>",
        "  <if test='roomTypeId != null'>",
        "    AND r.room_type_id = #{roomTypeId}",
        "  </if>",
        "  <if test='bedTypeId != null'>",
        "    AND r.bed_type_id = #{bedTypeId}",
        "  </if>",
        "  <choose>",
        "    <when test='onlyAvailable == null or onlyAvailable == true'>",
        "      AND r.is_available = TRUE",
        "    </when>",
        "    <otherwise>",
        "      AND r.is_available = FALSE",
        "    </otherwise>",
        "  </choose>",
        "GROUP BY",
        "  h.id, h.name, h.address, h.city, h.country, h.postal_code, h.phone_number, h.email, h.star_rating, h.description, h.total_rooms",
        "<choose>",
        "  <when test=\"sortBy == 'price'\">",
        "    ORDER BY lowestPrice ASC, h.name ASC",
        "  </when>",
        "  <otherwise>",
        "    ORDER BY h.name ASC",
        "  </otherwise>",
        "</choose>",
        "</script>"
})
List<Hotel> searchHotels(
        @Param("name") String name,
        @Param("city") String city,
        @Param("starRating") Integer starRating,
        @Param("priceFrom") BigDecimal priceFrom,
        @Param("priceTo") BigDecimal priceTo,
        @Param("roomTypeId") Integer roomTypeId,
        @Param("bedTypeId") Integer bedTypeId,
        @Param("onlyAvailable") Boolean onlyAvailable,
        @Param("sortBy") String sortBy

);


}