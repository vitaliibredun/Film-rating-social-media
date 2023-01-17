package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.genre.GenreStorage;
import ru.yandex.practicum.filmorate.exceptions.GenreDoesNotExistException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Service
public class GenreService {
    private final GenreStorage genreStorage;

    @Autowired
    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre findGenre(Integer id) {
        boolean idExists = findAllGenres().stream()
                .map(Genre::getId)
                .anyMatch(id::equals);
        if (!idExists) {
            throw new GenreDoesNotExistException("The Genre with the id doesn't exists");
        }
        return genreStorage.findGenre(id);
    }

    public Collection<Genre> findAllGenres() {
        return genreStorage.findAllGenres();
    }
}
