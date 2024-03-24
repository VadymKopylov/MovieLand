create table if not exists genres (
    id   bigserial not null,
    name varchar(255),
    primary key (id)
);