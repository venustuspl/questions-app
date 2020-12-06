DROP TABLE IF EXISTS categories, questions, answers;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE categories (
	id uuid NOT NULL DEFAULT uuid_generate_v4(),
	name varchar(255) NOT NULL,
	parent uuid,
    PRIMARY KEY (id),
    FOREIGN KEY (parent) REFERENCES categories(id)
);

CREATE TABLE questions (
	id uuid NOT NULL DEFAULT uuid_generate_v4(),
	name text NULL,
	category_id uuid NOT NULL,
	creation_date timestamptz(0) NULL DEFAULT (CURRENT_DATE + CURRENT_TIME),
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE answers (
	id uuid NOT NULL DEFAULT uuid_generate_v4(),
	body text NULL,
	question_id uuid NOT NULL,
	creation_date timestamptz(0) NULL DEFAULT (CURRENT_DATE + CURRENT_TIME),
	PRIMARY KEY (id),
	FOREIGN KEY (question_id) REFERENCES questions(id)
);

CREATE TABLE forbidden_words (
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    word text,
    PRIMARY KEY (id)
);