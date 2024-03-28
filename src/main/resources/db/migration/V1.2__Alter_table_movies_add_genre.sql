CREATE TABLE movie_genre
(
    movie_id INTEGER NOT NULL,
    genre_id INTEGER NOT NULL
);

ALTER TABLE movie_genre
    ADD CONSTRAINT movie_id_fk FOREIGN KEY (movie_id) REFERENCES "movies" (id);
ALTER TABLE movie_genre
    ADD CONSTRAINT genre_id_fk FOREIGN KEY (genre_id) REFERENCES "genres" (id);
