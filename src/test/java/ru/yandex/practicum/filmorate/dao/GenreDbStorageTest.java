package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.dao.film.FilmStorage;
import ru.yandex.practicum.filmorate.dao.genre.GenreStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;

@SpringBootTest
@AutoConfigureTestDatabase
public class GenreDbStorageTest {
    @Qualifier("filmDbStorage")
    @Autowired
    private FilmStorage filmStorage;
    @Autowired
    private GenreStorage genreStorage;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void cleanUp() {
        jdbcTemplate.execute("DELETE FROM films");
        jdbcTemplate.execute("ALTER TABLE films ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("DELETE FROM film_genre");
    }

    @Test
    @DisplayName("Проверка поиска жанра по id")
    void findGenreTest() {
        Assertions.assertAll(
                () -> Assertions.assertEquals("Комедия", genreStorage.findGenre(1).getName(), "Данные не верны"),
                () -> Assertions.assertEquals("Драма", genreStorage.findGenre(2).getName(), "Данные не верны"),
                () -> Assertions.assertEquals("Триллер", genreStorage.findGenre(4).getName(), "Данные не верны")
        );
    }

    @Test
    @DisplayName("Проверка поиска всех жанров")
    void findAllGenresTest() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(6, genreStorage.findAllGenres().size(), "Данные не верны"),
                () -> Assertions.assertEquals("Комедия", genreStorage.findGenre(1).getName(), "Данные не верны"),
                () -> Assertions.assertEquals("Драма", genreStorage.findGenre(2).getName(), "Данные не верны"),
                () -> Assertions.assertEquals("Мультфильм", genreStorage.findGenre(3).getName(), "Данные не верны"),
                () -> Assertions.assertEquals("Триллер", genreStorage.findGenre(4).getName(), "Данные не верны"),
                () -> Assertions.assertEquals("Документальный", genreStorage.findGenre(5).getName(), "Данные не верны"),
                () -> Assertions.assertEquals("Боевик", genreStorage.findGenre(6).getName(), "Данные не верны")
        );
    }
}
