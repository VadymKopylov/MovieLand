ALTER TABLE users
    ADD COLUMN "email"    VARCHAR(64) NOT NULL,
    ADD COLUMN "password" VARCHAR(64) NOT NULL;

CREATE TABLE roles
(
    "id"   INTEGER PRIMARY KEY,
    "name" VARCHAR(64) NOT NULL
);

CREATE TABLE users_roles
(
    "user_id" BIGINT  NOT NULL,
    "role_id" INTEGER NOT NULL,
    PRIMARY KEY ("user_id", "role_id"),
    CONSTRAINT "fk_user_id" FOREIGN KEY ("user_id") REFERENCES "users" ("id"),
    CONSTRAINT "fk_role_id" FOREIGN KEY ("role_id") REFERENCES "roles" ("id")
);