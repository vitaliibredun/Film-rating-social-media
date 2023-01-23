package ru.yandex.practicum.filmorate.exceptions;

public class MpaDoesNotExistException extends RuntimeException {
    public MpaDoesNotExistException(final String message) {
        super(message);
    }
}
