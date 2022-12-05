package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage{
    protected static final Map<Integer, Film> films = new HashMap<>();
    private int counter = 0;
    private int setInId() {
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
    public Film findFilmById(int id) {
        if (!films.containsKey(id)) {
            throw new FilmNotFoundException("The film with the id doesn't exist");
        }
        return films.get(id);
    }
}
