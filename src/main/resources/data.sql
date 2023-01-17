DELETE FROM users;
ALTER TABLE users ALTER COLUMN id RESTART WITH 1;
DELETE FROM films;
ALTER TABLE films ALTER COLUMN id RESTART WITH 1;
DELETE FROM film_mpa;
ALTER TABLE film_mpa ALTER COLUMN id RESTART WITH 1;
DELETE FROM genre;
ALTER TABLE genre ALTER COLUMN id RESTART WITH 1;

INSERT INTO film_mpa (name)
VALUES ('G');
INSERT INTO film_mpa (name)
VALUES ('PG');
INSERT INTO film_mpa (name)
VALUES ('PG-13');
INSERT INTO film_mpa (name)
VALUES ('R');
INSERT INTO film_mpa (name)
VALUES ('NC-17');

INSERT INTO genre (name)
VALUES ('Комедия');
INSERT INTO genre (name)
VALUES ('Драма');
INSERT INTO genre (name)
VALUES ('Мультфильм');
INSERT INTO genre (name)
VALUES ('Триллер');
INSERT INTO genre (name)
VALUES ('Документальный');
INSERT INTO genre (name)
VALUES ('Боевик');




