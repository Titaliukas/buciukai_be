-- Add sample hotel
INSERT INTO buciukai.hotel (name, address, city, country, postal_code, phone_number, email, star_rating, description, total_rooms)
VALUES ('Grand Plaza Hotel', '123 Main Street', 'Vilnius', 'Lithuania', '01234', '+370 5 123 4567', 'info@grandplaza.lt', 4, 'Luxurious 4-star hotel in the heart of Vilnius', 10);

-- Add sample rooms to hotel with id 1
INSERT INTO buciukai.room (room_number, price, floor_number, is_available, description, size_m2, room_type_id, bed_type_id, hotel_id)
VALUES
    (101, 89.99, 1, true, 'Cozy single room with city view', 18.5, 1, 1, 1),
    (102, 129.99, 1, true, 'Comfortable double room with balcony', 25.0, 2, 2, 1),
    (103, 149.99, 1, true, 'Spacious double room with king size bed', 28.0, 2, 3, 1),
    (201, 179.99, 2, true, 'Triple room perfect for families', 32.0, 3, 2, 1),
    (202, 249.99, 2, true, 'Luxurious suite with separate living area', 45.0, 4, 3, 1),
    (203, 299.99, 2, true, 'Premium deluxe suite with panoramic view', 55.0, 5, 3, 1),
    (301, 89.99, 3, true, 'Budget-friendly single room', 16.0, 1, 1, 1),
    (302, 139.99, 3, true, 'Modern double room with workspace', 26.0, 2, 2, 1),
    (303, 189.99, 3, true, 'Family triple room with bunk beds', 30.0, 3, 4, 1),
    (304, 119.99, 3, true, 'Standard double room', 24.0, 2, 2, 1);
