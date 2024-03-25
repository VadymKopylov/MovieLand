create table if not exists movies (
                       year_of_release varchar(255),
                       price float(53) not null,
                       rating float(53) not null,
                       id bigserial not null,
                       name_native varchar(255),
                       name_russian varchar(255),
                       picture_path varchar(255),
                       primary key (id)
)