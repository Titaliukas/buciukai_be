# Viešbučiu rezervavimo sistema

## Paleidimas

1. buciukai_be/src/main/resources įkelti private-key.json failą
2. Atsidaryti Docker Dekstop programą
3. terminale paleisti `docker compose up -d`
4. Toliau `.\gradlew bootrun`

## DB migracijos

sql migracijas rašom buciukai_be/src/main/resources/db/migrations aplanke.
Numeruojam iš eilės V1_someChange.sql, V2_..., V3_...
Nekeičiame ankstesnių rašytų migracijų, jei jos jau buvo supushintos i main.