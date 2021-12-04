DROP TABLE IF EXISTS page, book, "user" CASCADE;

CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    username VARCHAR,
    password VARCHAR,
    email VARCHAR,
    name VARCHAR,
    surname VARCHAR
);

CREATE TABLE book (
    id SERIAL PRIMARY KEY,
    title varchar,
    user_id BIGINT,
    link varchar,
    FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);

CREATE TABLE page (
    id SERIAL PRIMARY KEY,
    text varchar,
    number integer,
    book_id BIGINT,
    FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE
);
