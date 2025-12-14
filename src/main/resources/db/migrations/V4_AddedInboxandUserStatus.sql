CREATE TABLE user_status (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO user_status (id, name) VALUES
(1, 'ACTIVE'),
(2, 'BLOCKED');


ALTER TABLE users
ADD COLUMN status_id INT NOT NULL DEFAULT 1;

ALTER TABLE users
ADD CONSTRAINT fk_users_status
FOREIGN KEY (status_id)
REFERENCES user_status(id);


CREATE TABLE user_inbox (
    id SERIAL PRIMARY KEY,
    user_id UUID NOT NULL,
    announcement_id INT NOT NULL,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    received_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_inbox_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_user_inbox_announcement
        FOREIGN KEY (announcement_id)
        REFERENCES announcement(id)
        ON DELETE CASCADE
);