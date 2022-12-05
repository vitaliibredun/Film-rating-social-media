package ru.yandex.practicum.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.time.LocalDate;

@Slf4j
public class FilmValidation extends InMemoryFilmStorage {
    public static void filmValidator(Film film) {
        if (film.getName().isEmpty()) {
            log.error("Validation failed. The name is empty {}", film.getName());
            throw new ValidationException("Название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.error("Validation failed. " +
                    "The maximum length of description is 200, but was {}", film.getDescription().length());
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Validation failed. " +
                    "The date of the release before 1895-12-28 {} ", film.getReleaseDate());
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() < 0) {
            log.error("Validation failed. The duration of the film is negative {}", film.getDuration());
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
        if (!films.containsKey(film.getId())) {
            if (!films.isEmpty()) {
                if (film.getId() != 0) {
                    log.error("Validation failed. The film with the id doesn't exist {}", film.getId());
                    throw new FilmNotFoundException("Фильма с таким id не существует");
                }
            }
        }
    }
}
