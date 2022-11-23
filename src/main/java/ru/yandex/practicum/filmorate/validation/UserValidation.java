package ru.yandex.practicum.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class UserValidation extends UserController{
    public static void userValidator(User user) {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            log.error("Валидация не пройдена");
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin().contains(" ") || user.getLogin().isEmpty()) {
            log.error("Валидация не пройдена");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
            System.out.println("Имя пользователя установлено по значению логина");
            return;
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Валидация не пройдена");
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        if (!users.isEmpty() && !users.containsKey(user.getId())) {
            log.error("Валидация не пройдена");
            throw new ValidationException("Пользователя с таким id не существует");
        }
    }
}
