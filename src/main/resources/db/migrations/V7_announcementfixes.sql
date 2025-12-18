ALTER TABLE announcement
ADD COLUMN type VARCHAR(20) NOT NULL DEFAULT 'NEWS';

ALTER TABLE announcement
DROP CONSTRAINT IF EXISTS fk_announcement_admin;

ALTER TABLE announcement
ADD CONSTRAINT fk_announcement_admin
FOREIGN KEY (admin_id)
REFERENCES users(id);
