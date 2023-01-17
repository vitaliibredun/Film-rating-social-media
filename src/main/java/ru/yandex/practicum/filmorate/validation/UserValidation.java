package ru.yandex.practicum.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.user.UserStorage;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Component
@Slf4j
public class UserValidation {
    private UserStorage userStorage;

    @Autowired
    public UserValidation(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public UserValidation() {

    }

    public void userValidator(User user) {
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
        if (!userStorage.findAllUsers().isEmpty() && user.getId() != null) {
            boolean userExist = userStorage.findAllUsers().stream().map(User::getId).anyMatch(user.getId()::equals);
            if (!userExist) {
                throw new FilmNotFoundException("The user with the id doesn't exists");
            }
        }
    }
}
