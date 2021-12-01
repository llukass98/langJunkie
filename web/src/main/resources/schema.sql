DROP TABLE IF EXISTS dictionary, dictionary_definitions CASCADE;

CREATE TABLE dictionary (
    id SERIAL PRIMARY KEY,
    language TEXT,
    word TEXT,
    name TEXT,
    link TEXT
)
