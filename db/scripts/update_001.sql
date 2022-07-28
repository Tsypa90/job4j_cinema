CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  email VARCHAR NOT NULL UNIQUE,
  phone VARCHAR NOT NULL UNIQUE
);

CREATE TABLE films (
  id SERIAL PRIMARY KEY,
  name text
);

CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES films(id),
    pos_row INT NOT NULL unique,
    cell INT NOT NULL unique,
    user_id INT NOT NULL REFERENCES users(id)
);

create table cinema (
	session_id int not null references sessions(id),
	row int not null,
	seat int not null
);

insert into cinema (session_id, row, seat) values (1, 1, 1);
insert into cinema (session_id, row, seat) values (1, 1, 2);
insert into cinema (session_id, row, seat) values (1, 1, 3);
insert into cinema (session_id, row, seat) values (1, 1, 4);
insert into cinema (session_id, row, seat) values (1, 1, 5);
insert into cinema (session_id, row, seat) values (1, 2, 1);
insert into cinema (session_id, row, seat) values (1, 2, 2);
insert into cinema (session_id, row, seat) values (1, 2, 3);
insert into cinema (session_id, row, seat) values (1, 2, 4);
insert into cinema (session_id, row, seat) values (1, 2, 5);
insert into cinema (session_id, row, seat) values (1, 3, 1);
insert into cinema (session_id, row, seat) values (1, 3, 2);
insert into cinema (session_id, row, seat) values (1, 3, 3);
insert into cinema (session_id, row, seat) values (1, 3, 4);
insert into cinema (session_id, row, seat) values (1, 3, 5);

insert into cinema (session_id, row, seat) values (2, 1, 1);
insert into cinema (session_id, row, seat) values (2, 1, 2);
insert into cinema (session_id, row, seat) values (2, 1, 3);
insert into cinema (session_id, row, seat) values (2, 1, 4);
insert into cinema (session_id, row, seat) values (2, 1, 5);
insert into cinema (session_id, row, seat) values (2, 2, 1);
insert into cinema (session_id, row, seat) values (2, 2, 2);
insert into cinema (session_id, row, seat) values (2, 2, 3);
insert into cinema (session_id, row, seat) values (2, 2, 4);
insert into cinema (session_id, row, seat) values (2, 2, 5);
insert into cinema (session_id, row, seat) values (2, 3, 1);
insert into cinema (session_id, row, seat) values (2, 3, 2);
insert into cinema (session_id, row, seat) values (2, 3, 3);
insert into cinema (session_id, row, seat) values (2, 3, 4);
insert into cinema (session_id, row, seat) values (2, 3, 5);

insert into cinema (session_id, row, seat) values (3, 1, 1);
insert into cinema (session_id, row, seat) values (3, 1, 2);
insert into cinema (session_id, row, seat) values (3, 1, 3);
insert into cinema (session_id, row, seat) values (3, 1, 4);
insert into cinema (session_id, row, seat) values (3, 1, 5);
insert into cinema (session_id, row, seat) values (3, 2, 1);
insert into cinema (session_id, row, seat) values (3, 2, 2);
insert into cinema (session_id, row, seat) values (3, 2, 3);
insert into cinema (session_id, row, seat) values (3, 2, 4);
insert into cinema (session_id, row, seat) values (3, 2, 5);
insert into cinema (session_id, row, seat) values (3, 3, 1);
insert into cinema (session_id, row, seat) values (3, 3, 2);
insert into cinema (session_id, row, seat) values (3, 3, 3);
insert into cinema (session_id, row, seat) values (3, 3, 4);
insert into cinema (session_id, row, seat) values (3, 3, 5);