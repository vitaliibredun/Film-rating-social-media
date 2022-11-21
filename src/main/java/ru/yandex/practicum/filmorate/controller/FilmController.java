package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

import static ru.yandex.practicum.filmorate.validation.FilmValidation.filmValidator;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    protected static final Map<Integer, Film> films = new HashMap<>();
    private int counter = 0;

    @GetMapping
    public Collection<Film> findAllFilms() {
        return films.values();
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        log.debug("Film: {}", film);
        filmValidator(film);
        film.setId(++counter);
        films.put(counter, film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        log.debug("Film: {}", film);
        int id = film.getId();
        filmValidator(film);
        films.put(id, film);
        return film;
    }
}
