ALTER TABLE buciukai.event
    DROP COLUMN IF EXISTS date,
    DROP COLUMN IF EXISTS time_start,
    DROP COLUMN IF EXISTS time_end;

ALTER TABLE buciukai.event
    ADD COLUMN start_at TIMESTAMP NOT NULL,
    ADD COLUMN end_at TIMESTAMP NOT NULL;


ALTER TABLE buciukai.event
    ADD COLUMN hotel_id INT NOT NULL;

ALTER TABLE buciukai.event
    ADD CONSTRAINT fk_event_hotel
        FOREIGN KEY (hotel_id)
        REFERENCES hotel(id);

