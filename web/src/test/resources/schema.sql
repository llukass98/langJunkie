DROP TABLE IF EXISTS
    role,
    dictionary,
    dictionary_definitions,
    "user",
    card,
    image_file_info CASCADE;

CREATE TABLE dictionary (
    id BIGSERIAL PRIMARY KEY,
    language VARCHAR,
    word VARCHAR,
    name VARCHAR,
    link VARCHAR
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
    role_id BIGINT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE image_file_info (
    id BIGSERIAL PRIMARY KEY,
    filename VARCHAR,
    mime_type VARCHAR,
    original_name VARCHAR,
    size BIGINT
);

CREATE TABLE card (
    id BIGSERIAL PRIMARY KEY,
    front_side VARCHAR,
    back_side VARCHAR,
    language VARCHAR,
    word VARCHAR,
    user_id BIGINT,
    image_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES "user"(id),
    FOREIGN KEY (image_id) REFERENCES image_file_info(id)
);

INSERT INTO role(id, name) VALUES (1, 'ADMIN');
INSERT INTO role(id, name) VALUES (2, 'USER');

INSERT INTO "user"
    (username, password, email, full_name, role_id)
VALUES
    ('admin', '$2a$10$UhVwh1Kxrre3df1MkAexvuNw792lrrAU2y6A5PAYoouZ89cqg0kDK',
     'admin@admin.org', 'John Dough', 1),
    ('user', '$2a$10$UhVwh1Kxrre3df1MkAexvuNw792lrrAU2y6A5PAYoouZ89cqg0kDK',
     'user@user.org', 'John Dough Jr.', 2);

INSERT INTO  image_file_info
    (filename, mime_type, original_name, size)
VALUES
    ('1234-6456-23423-425gdf-gfewr-245.jpg', 'image/jpeg', 'image.jpg', 209785);

INSERT INTO card
    (front_side, back_side, language, user_id, image_id, word)
VALUES
    (E'This is what I\'m capable of', 'Back side', 'FAEN', 1, null, 'capable'),
    (E'This is what I\'m capable of', 'Back side', 'FAEN', 1, 1,    'capable'),
    (E'This is what I\'m capable of', 'Back side', 'FAEN', 1, null, 'capable'),
    (E'This is what I\'m capable of', 'Back side', 'FAEN', 1, null, 'capable'),
    (E'This is what I\'m capable of', 'Back side', 'FAEN', 1, null, 'capable'),
    (E'This is what I\'m capable of', 'Back side', 'FAEN', 1, null, 'capable'),
    (E'This is what I\'m capable of', 'Back side', 'FAEN', 1, null, 'capable'),
    (E'This is what I\'m capable of', 'Back side', 'FAEN', 1, null, 'capable');
