package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class InMemoryUserStorage implements UserStorage{
    protected static final Map<Integer, User> users = new HashMap<>();
    private int counter = 0;
    private int setInId() {
        return ++counter;
    }

    public Set<Integer> findAllUsersIds() {
        return users.keySet();
    }

    @Override
    public User addUser(User user) {
        user.setId(setInId());
        users.put(counter, user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        int userId = user.getId();
        users.put(userId, user);
        return user;
    }

    @Override
    public Collection<User> findAllUsers() {
        return users.values();
    }

    @Override
    public User findUserById(int id) {
        if (!users.containsKey(id)) {
            throw new UserNotFoundException("Пользователя с таким id не существует");
        }

        return users.get(id);
    }
}
