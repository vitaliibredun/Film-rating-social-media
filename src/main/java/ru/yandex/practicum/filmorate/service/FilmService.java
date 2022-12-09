package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.service.checking.FilmCheck;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final FilmValidation filmValidation;
    private final FilmCheck filmCheck;

    @Autowired
    public FilmService(FilmStorage filmStorage,
                       FilmValidation filmValidation,
                       FilmCheck filmCheck) {
        this.filmStorage = filmStorage;
        this.filmValidation = filmValidation;
        this.filmCheck = filmCheck;
    }

    public Film addFilm(Film film) {
        filmCheck.checkThereIsNoFilm(film);
        filmValidation.filmValidator(film);
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        filmCheck.checkFilmExists(film);
        filmValidation.filmValidator(film);
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    public Film findFilmById(Integer filmId) {
        filmCheck.checkFilmExistsById(filmId);
        return filmStorage.findFilmById(filmId);
    }

    public void likeToFilm(Integer filmId, Integer userId) {
        Film filmById = filmStorage.findFilmById(filmId);
        filmById.addLike(userId);
    }

    public void deleteLikeToFilm(Integer filmId, Integer userId) {
        Film filmById = filmStorage.findFilmById(filmId);
        filmCheck.checkUserExistsByLikes(filmId, userId);
        Set<Integer> likesToFilm = filmById.getLikes();
        likesToFilm.remove(userId);
    }

    public List<Film> findFilmsByLikes(Integer count) {
        Collection<Film> allFilms = filmStorage.findAllFilms();

        return allFilms.stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(film->film.getLikes().size())))
                .limit(count)
                .collect(Collectors.toList());
    }
}
