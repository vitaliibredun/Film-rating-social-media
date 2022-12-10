package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage{
    private final Map<Integer, User> users = new HashMap<>();
    private Integer counter = 0;
    private Integer setInId() {
        return ++counter;
    }

    @Override
    public User addUser(User user) {
        if (users.containsValue(user)) {
            throw new UserAlreadyExistException("The user is already exists");
        }
        user.setId(setInId());
        users.put(counter, user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new FilmNotFoundException("The user with the id doesn't exists");
        }
        int userId = user.getId();
        users.put(userId, user);
        return user;
    }

    @Override
    public Collection<User> findAllUsers() {
        return users.values();
    }

    @Override
    public User findUserById(Integer userId) {
        if (!users.containsKey(userId)) {
            throw new UserNotFoundException("The user with the id doesn't exists");
        }
        return users.get(userId);
    }
}
