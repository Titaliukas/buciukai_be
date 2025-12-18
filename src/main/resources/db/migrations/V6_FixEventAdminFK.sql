ALTER TABLE buciukai.event
DROP CONSTRAINT fk_event_admin;

ALTER TABLE buciukai.event
ADD CONSTRAINT fk_event_admin
FOREIGN KEY (admin_id)
REFERENCES buciukai.users(id);
