package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final FilmValidation filmValidation;

    @Autowired
    public FilmService(FilmStorage filmStorage, FilmValidation filmValidation) {
        this.filmStorage = filmStorage;
        this.filmValidation = filmValidation;
    }

    public Film addFilm(Film film) {
        filmValidation.filmValidator(film);
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        filmValidation.filmValidator(film);
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    public Film findFilmById(Integer filmId) {
        return filmStorage.findFilmById(filmId);
    }

    public void likeToFilm(Integer filmId, Integer userId) {
        Film filmById = filmStorage.findFilmById(filmId);
        filmById.addLike(userId);
    }

    public void deleteLikeToFilm(Integer filmId, Integer userId) {
        Film filmById = filmStorage.findFilmById(filmId);
        if (!filmById.getLikes().contains(userId)) {
            throw new UserNotFoundException("The user not found");
        }
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
