# java-filmorate

![ER diagram](https://github.com/vitaliibredun/java-filmorate/blob/main/src/main/resources/ER%20diagram.png?raw=true)

#### user
Содержит информацию о пользователе, внешний ключ friends_id ссылается на список друзей пользователя.

#### film
Содержит информацию о фильмах, по первичному ключу film_id связь один ко многим с таблицей **film_likes** для получения информации о рейтинге фильма по лайкам. Таблица **film_genre** для получения имени жанра фильма.



### Request Types

#### Get all films
```java
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
```
#### Get 5 best films
```java
SELECT f.name
FROM film AS f
FULL OUTER JOIN film_likes AS fl ON f.film_id = fl.film_id
GROUP BY f.name
ORDER BY COUNT(fl.likes_id) DESC
LIMIT 5;
```

#### Get all users
```java
SELECT email,
       login,
       name,
       CAST (birthday AS date)
FROM user
```

#### Get common friends between 2 users
```java
SELECT DISTINCT f.name
FROM user AS u
FULL OUTER JOIN user AS f ON u.friends_id = f.user_id
WHERE u.user_id IN (1, 2)
```

#### Get user's friends
```java
SELECT f.*
FROM user AS u
FULL OUTER JOIN user AS f ON u.friends_id = f.user_id
WHERE u.user_id = 1
```
















