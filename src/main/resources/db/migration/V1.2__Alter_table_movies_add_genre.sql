ALTER TABLE movies
    ADD COLUMN genre_id BIGINT;

ALTER TABLE movies
    ADD CONSTRAINT fk_genre_id FOREIGN KEY (genre_id) REFERENCES genres (id);
