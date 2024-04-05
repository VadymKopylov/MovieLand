CREATE TABLE reviews (
            "id" INTEGER PRIMARY KEY,
            "text" VARCHAR(500) NOT NULL
);

CREATE TABLE users(
                      "id" INTEGER PRIMARY KEY,
                      "nickname" VARCHAR(45) NOT NULL
);

ALTER TABLE reviews ADD COLUMN "movie_id" INTEGER;
ALTER TABLE reviews ADD CONSTRAINT movie_id FOREIGN KEY (movie_id) REFERENCES movies (id);
ALTER TABLE reviews ADD COLUMN "user_id" INTEGER;
ALTER TABLE reviews ADD CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES users (id);