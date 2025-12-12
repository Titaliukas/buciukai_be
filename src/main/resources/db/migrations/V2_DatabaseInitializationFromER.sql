DROP TABLE IF EXISTS staff CASCADE;
DROP TABLE IF EXISTS clients CASCADE;

CREATE TABLE bed_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

INSERT INTO bed_type (id, name) VALUES
(1, 'single'),
(2, 'double'),
(3, 'king'),
(4, 'bunk');

CREATE TABLE hotel (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    postal_code VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    star_rating INT NOT NULL,
    description VARCHAR(255) NOT NULL,
    total_rooms INT NOT NULL
);

CREATE TABLE report_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    default_parameters_json JSONB NOT NULL
);

CREATE TABLE reservation_status (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

INSERT INTO reservation_status (id, name) VALUES
(1, 'confirmed'),
(2, 'cancelled'),
(3, 'completed');

CREATE TABLE room_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

INSERT INTO room_type (id, name) VALUES
(1, 'single'),
(2, 'double'),
(3, 'triple'),
(4, 'suite'),
(5, 'deluxe');

CREATE TABLE system_setting (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL,
    description VARCHAR(255) NOT NULL
);


CREATE TABLE admin (
    user_id INT PRIMARY KEY,

    CONSTRAINT fk_admin_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);

CREATE TABLE client (
    user_id INT PRIMARY KEY,
    total_reservations INT,

    CONSTRAINT fk_client_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);

CREATE TABLE room (
    id SERIAL PRIMARY KEY,
    room_number INT NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    floor_number INT NOT NULL,
    is_available BOOLEAN NOT NULL,
    description VARCHAR(255) NOT NULL,
    size_m2 NUMERIC(10,2) NOT NULL,
    room_type_id INT NOT NULL,
    bed_type_id INT NOT NULL,
    hotel_id INT NOT NULL,

    CONSTRAINT fk_room_room_type
        FOREIGN KEY (room_type_id)
        REFERENCES room_type(id),
    
    CONSTRAINT fk_room_bed_type
        FOREIGN KEY (bed_type_id)
        REFERENCES bed_type(id),
    
    CONSTRAINT fk_room_hotel
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
    user_id INT NOT NULL,

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

CREATE TABLE staff (
    user_id INT PRIMARY KEY,
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

CREATE TABLE announcement (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    message VARCHAR(255) NOT NULL,
    visible_until DATE NOT NULL,
    admin_id INT NOT NULL,

    CONSTRAINT fk_announcement_admin
        FOREIGN KEY (admin_id)
        REFERENCES admin(user_id)
);

CREATE TABLE availability_slot (
    id SERIAL PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    room_id INT NOT NULL,

    CONSTRAINT fk_availability_slot_room
        FOREIGN KEY (room_id)
        REFERENCES room(id)
);

CREATE TABLE event (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    time_start TIME NOT NULL,
    time_end TIME NOT NULL,
    admin_id INT NOT NULL,

    CONSTRAINT fk_event_admin
        FOREIGN KEY (admin_id)
        REFERENCES admin(user_id)
);

CREATE TABLE exclusion (
    id SERIAL PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    room_id INT NOT NULL,

    CONSTRAINT fk_exclusion_room
        FOREIGN KEY (room_id)
        REFERENCES room(id)
);

CREATE TABLE filter_option (
    id SERIAL PRIMARY KEY,
    filter_type VARCHAR(255) NOT NULL,
    filter_value VARCHAR(255) NOT NULL,
    search_query_id INT NOT NULL,

    CONSTRAINT fk_filter_option_search_query
        FOREIGN KEY (search_query_id)
        REFERENCES search_query(id)
);

CREATE TABLE report (
    id SERIAL PRIMARY KEY,
    generation_date DATE NOT NULL,
    period_start DATE NOT NULL,
    period_end DATE NOT NULL,
    parameters_json JSONB NOT NULL,
    report_type_id INT NOT NULL,
    admin_id INT NOT NULL,

    CONSTRAINT fk_report_report_type
        FOREIGN KEY (report_type_id)
        REFERENCES report_type(id),
    
    CONSTRAINT fk_report_admin
        FOREIGN KEY (admin_id)
        REFERENCES admin(user_id)
);

CREATE TABLE reservation (
    id SERIAL PRIMARY KEY,
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,
    status INT NOT NULL,
    client_id INT NOT NULL,
    room_id INT NOT NULL,

    CONSTRAINT fk_reservation_status
        FOREIGN KEY (status)
        REFERENCES reservation_status(id),
    
    CONSTRAINT fk_reservation_client
        FOREIGN KEY (client_id)
        REFERENCES client(user_id),
    
    CONSTRAINT fk_reservation_room
        FOREIGN KEY (room_id)
        REFERENCES room(id)
);