package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.genre.GenreStorage;
import ru.yandex.practicum.filmorate.dao.user.UserStorage;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.dao.film.FilmStorage;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.FieldValidation;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import java.util.*;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final FilmValidation filmValidation;
    private final GenreStorage genreStorage;
    private final UserStorage userStorage;
    private final FieldValidation fieldValidation;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       FilmValidation filmValidation, GenreStorage genreStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage, FieldValidation fieldValidation) {
        this.filmStorage = filmStorage;
        this.filmValidation = filmValidation;
        this.genreStorage = genreStorage;
        this.userStorage = userStorage;
        this.fieldValidation = fieldValidation;
    }

    public Film addFilm(Film film) {
        filmValidation.filmValidator(film);
        film.setId(filmStorage.addFilm(film));
        genreStorage.addFilmToGenre(film);
        return film;
    }

    public Film updateFilm(Film film) {
        filmValidation.filmValidator(film);
        boolean checkFieldGenresIsNull = fieldValidation.checkFieldGenresIsNull(film);
        if (checkFieldGenresIsNull) {
            filmStorage.updateFilm(film);
            return film;
        }
        boolean fieldGenresIsEmpty = fieldValidation.checkFieldGenresIsEmpty(film);
        if (fieldGenresIsEmpty) {
            genreStorage.deleteGenreFromFilm(film);
            return film;
        }
        boolean fieldGenresIsPresent = fieldValidation.checkFieldGenresIsPresent(film);
        if (fieldGenresIsPresent) {
            genreStorage.deleteGenreFromFilm(film);
            genreStorage.addFilmToGenre(film);
            return filmStorage.updateFilm(film);
        }
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    public Film findFilmById(Integer filmId) {
        boolean filmExist = filmStorage.findAllFilms().stream().map(Film::getId).anyMatch(filmId::equals);
        if (!filmExist) {
            throw new FilmNotFoundException("The film with the id doesn't exists");
        }
        return filmStorage.findFilmById(filmId);
    }

    public void rateToFilm(Integer filmId, Integer userId) {
        filmStorage.addRateToFilm(filmId, userId);
    }

    public void deleteRateFromFilm(Integer filmId, Integer userId) {
        boolean userExist = userStorage.findAllUsers().stream().map(User::getId).anyMatch(userId::equals);
        if (!userExist) {
            throw new FilmNotFoundException("The user with the id doesn't exists");
        }
        filmStorage.deleteRateFromFilm(filmId, userId);
    }

    public List<Film> findFilmsByLikes(Integer count) {
        return filmStorage.findFilmsByRate(count);
    }
}
