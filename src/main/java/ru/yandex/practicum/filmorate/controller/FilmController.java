package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.*;


@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        log.debug("Film: {}", film);
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        log.debug("Film: {}", film);
        return filmService.updateFilm(film);
    }

    @GetMapping
    public Collection<Film> findAllFilms() {
        return filmService.findAllFilms();
    }

    @GetMapping("/{id}")
    public Film findFilmById(@PathVariable int id) {
        return filmService.findFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeToFilm(@PathVariable int id, @PathVariable int userId) {
        filmService.likeToFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLikeToFilm(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLikeToFilm(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> findFilmsByLikes(
            @RequestParam(value = "count", defaultValue = "10",required = false) int count) {
        return filmService.findFilmsByLikes(count);
    }
}
