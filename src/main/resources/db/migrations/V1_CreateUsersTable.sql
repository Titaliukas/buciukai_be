CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    firebase_uid VARCHAR(128) UNIQUE NOT NULL,
    username VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    birthdate TIMESTAMP WITH TIME ZONE,
    city VARCHAR(50),
    postal_code VARCHAR(50),
    role INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE clients (
    user_id UUID PRIMARY KEY,
    total_reservations INT DEFAULT 0,

    CONSTRAINT fk_client_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);
CREATE TABLE staff (
    user_id UUID PRIMARY KEY,
    position VARCHAR(100) NOT NULL,
    salary NUMERIC(10,2) NOT NULL,

    CONSTRAINT fk_staff_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);