package ru.yandex.practicum.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Slf4j
public class FilmValidation extends FilmController {
    public static void filmValidator(Film film) {
        if (film.getName().isEmpty()) {
            log.error("Валидация не пройдена");
            throw new ValidationException("Название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.error("Валидация не пройдена");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Валидация не пройдена");
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() < 0) {
            log.error("Валидация не пройдена");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
        if (!films.isEmpty() && !films.containsKey(film.getId())) {
            log.error("Валидация не пройдена");
            throw new ValidationException("Фильма с таким id не существует");
        }
    }
}
