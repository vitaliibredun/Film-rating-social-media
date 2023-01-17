package ru.yandex.practicum.filmorate.dao.genre;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.LinkedHashSet;

public interface GenreStorage {
    Genre findGenre(Integer id);

    Collection<Genre> findAllGenres();

    void addFilmToGenre(Film film);

    void deleteGenreFromFilm(Film film);

    LinkedHashSet<Genre> findFilmGenres(Integer id);
}
