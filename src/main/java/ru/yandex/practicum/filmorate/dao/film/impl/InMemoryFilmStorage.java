package ru.yandex.practicum.filmorate.dao.film.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.film.FilmStorage;
import ru.yandex.practicum.filmorate.exceptions.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component("inMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private Integer counter = 0;
    private Integer setInId() {
        return ++counter;
    }

    @Override
    public int addFilm(Film film) {
        if (films.containsValue(film)) {
            throw new FilmAlreadyExistException("The film is already exists");
        }
        film.setId(setInId());
        films.put(counter, film);
        return film.getId();
    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new FilmNotFoundException("The film with the id doesn't exists");
        }
        int filmId = film.getId();
        films.put(filmId, film);
        return film;
    }

    @Override
    public Collection<Film> findAllFilms() {
        return films.values();
    }

    @Override
    public Film findFilmById(Integer filmId) {
        if (!films.containsKey(filmId)) {
            throw new FilmNotFoundException("The film with the id doesn't exists");
        }
        return films.get(filmId);
    }

    @Override
    public void addRateToFilm(Integer filmId, Integer userId) {
        Film filmById = findFilmById(filmId);
        filmById.addLike(userId);
    }

    @Override
    public void deleteRateFromFilm(Integer filmId, Integer userId) {
        Film filmById = findFilmById(filmId);
        Set<Integer> likesToFilm = filmById.getLikes();
        likesToFilm.remove(userId);
    }

    @Override
    public List<Film> findFilmsByRate(Integer count) {
        Collection<Film> allFilms = findAllFilms();

        return allFilms.stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(film->film.getLikes().size())))
                .limit(count)
                .collect(Collectors.toList());
    }
}
