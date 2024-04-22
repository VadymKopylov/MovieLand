CREATE TABLE movies
(
    id              INTEGER PRIMARY KEY,
    name_russian    VARCHAR(100)   NOT NULL,
    name_native     VARCHAR(100)   NOT NULL,
    year_of_release VARCHAR(10)    NOT NULL,
    rating          DECIMAL(10, 2) NOT NULL,
    price           DECIMAL(10, 2) NOT NULL,
    picture_path    VARCHAR(255)   NOT NULL
);