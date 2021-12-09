DROP TABLE IF EXISTS dictionary, dictionary_definitions, "user", word, card CASCADE;

CREATE TABLE dictionary (
    id SERIAL PRIMARY KEY,
    language TEXT,
    word TEXT,
    name TEXT,
    link TEXT
);

CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR UNIQUE NOT NULL,
    password VARCHAR NOT NULL,
    email VARCHAR UNIQUE NOT NULL,
    full_name VARCHAR NOT NULL,
    role VARCHAR NOT NULL,
    is_active BOOLEAN NOT NULL
);

/*CREATE TABLE word (
    id BIGSERIAL PRIMARY KEY,
    word VARCHAR UNIQUE
);
*/
CREATE TABLE card (
    id BIGSERIAL PRIMARY KEY,
    front_side VARCHAR,
    back_side VARCHAR,
    language VARCHAR NOT NULL,
    user_id BIGINT,
    /*word_id BIGINT,*/
    word VARCHAR,
    FOREIGN KEY (user_id) REFERENCES "user"(id)
  /*  FOREIGN KEY (word_id) REFERENCES word(id)*/
);

INSERT INTO "user"
(username, password, email, full_name, role, is_active)
VALUES ('admin', '$2a$10$UhVwh1Kxrre3df1MkAexvuNw792lrrAU2y6A5PAYoouZ89cqg0kDK',
        'admin@admin.ru', 'John Dough', 'ROLE_ADMIN', TRUE);

/*INSERT INTO word (word) VALUES ('capable');*/

INSERT INTO card
(front_side, back_side, language, user_id, /*word_id*/ word)
VALUES (E'This is what I\'m capable of', 'Back side', 'enen', 1, 'capable');
