CREATE TABLE if not exists users (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  email VARCHAR NOT NULL UNIQUE,
  phone VARCHAR NOT NULL UNIQUE
);

CREATE TABLE if not exists films (
  id SERIAL PRIMARY KEY,
  name text
);

CREATE TABLE if not exists ticket (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES films(id),
    pos_row INT NOT NULL unique,
    cell INT NOT NULL unique,
    user_id INT NOT NULL REFERENCES users(id)
);

create table if not exists cinema (
	session_id int not null references films(id),
	row int not null,
	seat int not null
);