-- public.categories definition

-- Drop table

-- DROP TABLE categories;

CREATE TABLE categories (
	id uuid NOT NULL DEFAULT uuid_generate_v4(),
	name varchar(255) NULL,
	parent uuid NULL,
	CONSTRAINT categories_pkey PRIMARY KEY (id),
	CONSTRAINT categories_parent_fkey FOREIGN KEY (parent) REFERENCES categories(id)
);


-- public.questions definition

-- Drop table

-- DROP TABLE questions;

CREATE TABLE questions (
	id uuid NOT NULL DEFAULT uuid_generate_v4(),
	body text NULL,
	category_id uuid NOT NULL,
	creation_date timestamptz(0) NULL DEFAULT (CURRENT_DATE + CURRENT_TIME),
	CONSTRAINT questions_pkey PRIMARY KEY (id),
	CONSTRAINT questions_category_id_fkey FOREIGN KEY (category_id) REFERENCES categories(id)
);


-- public.answers definition

-- Drop table

-- DROP TABLE answers;

CREATE TABLE answers (
	id uuid NOT NULL DEFAULT uuid_generate_v4(),
	body text NULL,
	question_id uuid NOT NULL,
	creation_date timestamptz(0) NULL DEFAULT (CURRENT_DATE + CURRENT_TIME),
	CONSTRAINT answers_pkey PRIMARY KEY (id),
	CONSTRAINT answers_question_id_fkey FOREIGN KEY (question_id) REFERENCES questions(id)
);
