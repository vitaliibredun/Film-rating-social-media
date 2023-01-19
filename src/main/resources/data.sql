DELETE FROM users;
ALTER TABLE users ALTER COLUMN id RESTART WITH 1;
DELETE FROM films;
ALTER TABLE films ALTER COLUMN id RESTART WITH 1;
DELETE FROM film_mpa;
ALTER TABLE film_mpa ALTER COLUMN id RESTART WITH 1;
DELETE FROM genre;
ALTER TABLE genre ALTER COLUMN id_genre RESTART WITH 1;

INSERT INTO film_mpa (name_mpa)
VALUES ('G');
INSERT INTO film_mpa (name_mpa)
VALUES ('PG');
INSERT INTO film_mpa (name_mpa)
VALUES ('PG-13');
INSERT INTO film_mpa (name_mpa)
VALUES ('R');
INSERT INTO film_mpa (name_mpa)
VALUES ('NC-17');

INSERT INTO genre (name_genre)
VALUES ('Комедия');
INSERT INTO genre (name_genre)
VALUES ('Драма');
INSERT INTO genre (name_genre)
VALUES ('Мультфильм');
INSERT INTO genre (name_genre)
VALUES ('Триллер');
INSERT INTO genre (name_genre)
VALUES ('Документальный');
INSERT INTO genre (name_genre)
VALUES ('Боевик');




