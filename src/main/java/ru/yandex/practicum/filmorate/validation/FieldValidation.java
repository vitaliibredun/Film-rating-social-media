package ru.yandex.practicum.filmorate.validation;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Component
public class FieldValidation {
    public boolean checkFieldGenresIsEmpty(Film film) {
        return film.getGenres().size() == 0;
    }

    public boolean checkFieldGenresIsNull(Film film) {
        return film.getGenres() == null;
    }

    public boolean checkFieldGenresIsPresent(Film film) {
        Set<String> fieldsList = getFields(film.getClass());
        return ifFieldPresent(fieldsList, "genres");
    }

    private boolean ifFieldPresent(final Set<String> properties, final String name) {
        return properties.contains(name);
    }

    private Set<String> getFields(final Class<?> type) {
        Set<String> fields = new HashSet<>();

        for (Field field : type.getDeclaredFields()) {
            fields.add(field.getName());
        }
        return fields;
    }
}
