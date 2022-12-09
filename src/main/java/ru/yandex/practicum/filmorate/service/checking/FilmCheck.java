package ru.yandex.practicum.filmorate.service.checking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

@Service
public class FilmCheck {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmCheck(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void checkThereIsNoFilm(Film film) {
        if (filmStorage.findAllFilms().contains(film)) {
            throw new FilmAlreadyExistException("The film is already exists");
        }
    }

    public void checkFilmExists(Film film) {
        if (filmStorage.findFilmById(film.getId()) == null) {
            throw new FilmNotFoundException("The film with the id doesn't exist");
        }
    }

    public void checkUserExistsByLikes(Integer filmId, Integer userId) {
        Film filmById = filmStorage.findFilmById(filmId);
        if (!filmById.getLikes().contains(userId)) {
            throw new UserNotFoundException("The user not found");
        }
    }

    public void checkFilmExistsById(Integer filmId) {
        if (filmStorage.findFilmById(filmId) == null) {
            throw new FilmNotFoundException("The film with the id doesn't exists");
        }
    }
}
