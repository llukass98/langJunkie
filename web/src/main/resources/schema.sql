DROP TABLE IF EXISTS role, dictionary, dictionary_definitions, "user", word, card CASCADE;

CREATE TABLE dictionary (
    id SERIAL PRIMARY KEY,
    language TEXT,
    word TEXT,
    name TEXT,
    link TEXT
);

CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL
);

CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR UNIQUE NOT NULL,
    password VARCHAR NOT NULL,
    email VARCHAR UNIQUE NOT NULL,
    full_name VARCHAR NOT NULL,
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE card (
    id BIGSERIAL PRIMARY KEY,
    front_side VARCHAR,
    back_side VARCHAR,
    language VARCHAR NOT NULL,
    user_id BIGINT,
    word VARCHAR,
    FOREIGN KEY (user_id) REFERENCES "user"(id)
);

INSERT INTO role(id, name) VALUES (1, 'ADMIN');
INSERT INTO role(id, name) VALUES (2, 'USER');


INSERT INTO "user"
(username, password, email, full_name, role_id)
VALUES ('admin', '$2a$10$UhVwh1Kxrre3df1MkAexvuNw792lrrAU2y6A5PAYoouZ89cqg0kDK',
        'admin@admin.ru', 'John Dough', 1);

INSERT INTO card
(front_side, back_side, language, user_id, word)
VALUES (E'This is what I\'m capable of', 'Back side', 'enen', 1, 'capable');
