# java-filmorate
![ER diagram](https://github.com/vitaliibredun/java-filmorate/blob/main/src/main/resources/ER%20diagram.png?raw=true)

#### Request Types

### Get all films
'''java
SELECT f.name,
       f.description,
       CAST (f.release_date AS date),
       f.duration,
       f.rating,
       g.name,
       COUNT(fl.likes_id)
FROM film AS f
FULL OUTER JOIN film_genre AS fg ON f.film_id = fg.film.id
FULL OUTER JOIN genre AS g ON fg.genre_id = g.genre_id
FULL OUTER JOIN film_likes AS fl ON f.film_id = fl.film_id;
'''
