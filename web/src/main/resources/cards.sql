DROP TABLE IF EXISTS card, word, "user" CASCADE;

CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR,
    password VARCHAR,
    email VARCHAR,
    full_name VARCHAR,
    role VARCHAR,
    is_active BOOLEAN
);

CREATE TABLE word (
    id BIGSERIAL PRIMARY KEY,
    word VARCHAR
);

CREATE TABLE card (
    id BIGSERIAL PRIMARY KEY,
    front_side VARCHAR,
    back_side VARCHAR,
    user_id BIGINT,
    word_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES "user"(id),
    FOREIGN KEY (word_id) REFERENCES word(id)
);

INSERT INTO "user"
    (username, password, email, full_name, role, is_active)
VALUES ('admin', '$2a$10$UhVwh1Kxrre3df1MkAexvuNw792lrrAU2y6A5PAYoouZ89cqg0kDK',
        'admin@admin.ru', 'John Dough', 'ROLE_ADMIN', TRUE);
