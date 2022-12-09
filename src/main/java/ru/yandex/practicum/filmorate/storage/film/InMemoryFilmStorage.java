package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage{
    private final Map<Integer, Film> films = new HashMap<>();
    private Integer counter = 0;
    private Integer setInId() {
        return ++counter;
    }

    @Override
    public Film addFilm(Film film) {
        film.setId(setInId());
        films.put(counter, film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        int filmId = film.getId();
        films.put(filmId, film);
        return film;
    }

    @Override
    public Collection<Film> findAllFilms() {
        return films.values();
    }

    @Override
    public Film findFilmById(Integer id) {
        return films.get(id);
    }
}
