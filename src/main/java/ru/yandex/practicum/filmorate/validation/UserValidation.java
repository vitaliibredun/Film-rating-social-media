package ru.yandex.practicum.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;

@Slf4j
public class UserValidation extends InMemoryUserStorage {
    public static void userValidator(User user) {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            log.error("Validation failed. The email can't be empty or without @ symbol {}", user.getEmail());
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin().contains(" ") || user.getLogin().isEmpty()) {
            log.error("Validation failed. The login can't be empty or include space {}", user.getLogin());
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getName().isBlank() || user.getName().isEmpty()) {
            user.setName(user.getLogin());
            log.info("The name was empty,it has the same data as login");
            System.out.println("Имя пользователя установлено по значению логина");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Validation failed. The date of birthday is in the future {}", user.getBirthday());
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        if (!users.containsKey(user.getId()))
            if (!users.isEmpty()) {
                if (user.getId() != 0) {
                    log.error("Validation failed. The user with the id doesn't exist {}", user.getId());
                    throw new UserNotFoundException("Пользователя с таким id не существует");
                }
            }
    }
}
