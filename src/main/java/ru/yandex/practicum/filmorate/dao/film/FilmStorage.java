package ru.yandex.practicum.filmorate.dao.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {
    int addFilm(Film film);

    Film updateFilm(Film film);

    Collection<Film> findAllFilms();

    Film findFilmById(Integer id);

    void addRateToFilm(Integer filmId, Integer userId);

    List<Film> findFilmsByRate(Integer count);

    void deleteRateFromFilm(Integer filmId, Integer userId);
}
