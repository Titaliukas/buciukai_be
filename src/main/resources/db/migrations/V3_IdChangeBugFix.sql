DROP TABLE IF EXISTS admin CASCADE;
DROP TABLE IF EXISTS client CASCADE;
DROP TABLE IF EXISTS staff CASCADE;
DROP TABLE IF EXISTS search_query CASCADE;


CREATE TABLE admin (
    user_id UUID PRIMARY KEY,
    CONSTRAINT fk_admin_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);

CREATE TABLE client (
    user_id UUID PRIMARY KEY,
    total_reservations INT,
    CONSTRAINT fk_client_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);

CREATE TABLE staff (
    user_id UUID PRIMARY KEY,
    position VARCHAR(255),
    salary NUMERIC(10,2),
    hotel_id INT NOT NULL,
    CONSTRAINT fk_staff_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),
    CONSTRAINT fk_staff_hotel
        FOREIGN KEY (hotel_id)
        REFERENCES hotel(id)
);

CREATE TABLE search_query (
    id SERIAL PRIMARY KEY,
    search_date DATE NOT NULL,
    city VARCHAR(255) NOT NULL,
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,
    min_price NUMERIC(10,2) NOT NULL,
    max_price NUMERIC(10,2) NOT NULL,
    bed_type_id INT NOT NULL,
    room_type_id INT NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT fk_search_query_bed_type
        FOREIGN KEY (bed_type_id)
        REFERENCES bed_type(id),
    CONSTRAINT fk_search_query_room_type
        FOREIGN KEY (room_type_id)
        REFERENCES room_type(id),
    CONSTRAINT fk_search_query_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);
