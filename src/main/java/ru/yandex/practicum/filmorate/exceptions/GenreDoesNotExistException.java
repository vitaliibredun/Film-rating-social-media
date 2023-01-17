package ru.yandex.practicum.filmorate.exceptions;

public class GenreDoesNotExistException extends RuntimeException {
    public GenreDoesNotExistException(final String message) {
        super(message);
    }
}
