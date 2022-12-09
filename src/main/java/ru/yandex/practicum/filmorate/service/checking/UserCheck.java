package ru.yandex.practicum.filmorate.service.checking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Service
public class UserCheck {
    private final UserStorage userStorage;

    @Autowired
    public UserCheck(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void checkThereIsNoUser(User user) {
        if (userStorage.findAllUsers().contains(user)) {
            throw new UserAlreadyExistException("The user is already exists");
        }
    }

    public void checkUserExists(User user) {
        if (userStorage.findUserById(user.getId()) == null) {
            throw new FilmNotFoundException("The user with the id doesn't exists");
        }
    }

    public void checkFriendExists(Integer friendId) {
        if (userStorage.findUserById(friendId) == null) {
            throw new UserNotFoundException("The user not found");
        }
    }

    public void checkUserExistsById(Integer userId) {
        if (userStorage.findUserById(userId) == null) {
            throw new UserNotFoundException("Пользователя с таким id не существует");
        }
    }
}
