CREATE TABLE countries (
             id INTEGER PRIMARY KEY,
             name VARCHAR(45) NOT NULL
);

CREATE TABLE movie_country (
            movie_id INTEGER NOT NULL,
            country_id INTEGER NOT NULL
);

ALTER TABLE movie_country add CONSTRAINT "country_id" FOREIGN KEY("country_id") REFERENCES "countries"("id");
ALTER TABLE movie_country add CONSTRAINT "movie_id" FOREIGN KEY("movie_id") REFERENCES "movies"("id");