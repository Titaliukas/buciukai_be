ALTER TABLE buciukai.system_setting
ADD CONSTRAINT uq_system_setting_name UNIQUE (name);

INSERT INTO buciukai.system_setting (name, is_active, description)
VALUES
    (
        'SYSTEM_ACTIVE',
        TRUE,
        'Kontroliuoja sistemos prieinamuma klientams ir personalui, administracijai negalioja'
    ),
    (
        'REGISTRATION_ENABLED',
        TRUE,
        'Kontroliuoja registracija'
    )
ON CONFLICT (name) DO NOTHING;
