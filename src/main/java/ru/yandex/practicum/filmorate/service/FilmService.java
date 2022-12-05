package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.validation.FilmValidation.filmValidator;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film addFilm(Film film) {
        filmValidator(film);
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        if (filmStorage.findAllFilms().contains(film)) {
            throw new UserNotFoundException("The film is already exist");
        }

        filmValidator(film);
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    public Film findFilmById(int id) {
        return filmStorage.findFilmById(id);
    }

    public void likeToFilm(int id, int userId) {
        Film filmById = filmStorage.findFilmById(id);
        filmById.addLike(userId);
    }

    public void deleteLikeToFilm(int id, int userId) {
        Film filmById = filmStorage.findFilmById(id);
        if (!filmById.getLikes().contains(userId)) {
            throw new UserNotFoundException("The user not found");
        }
        Set<Integer> likesToFilm = filmById.getLikes();
        likesToFilm.remove(userId);
    }

    public List<Film> findFilmsByLikes(int count) {
        Collection<Film> allFilms = filmStorage.findAllFilms();

        return allFilms.stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(film->film.getLikes().size())))
                .limit(count)
                .collect(Collectors.toList());
    }
}
