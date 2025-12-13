DROP TABLE IF EXISTS report_type CASCADE;

ALTER TABLE report
DROP COLUMN IF EXISTS report_type_id,  
ALTER COLUMN period_end DROP NOT NULL,
ADD COLUMN IF NOT EXISTS report_name text NOT NULL DEFAULT 'Unnamed report';  