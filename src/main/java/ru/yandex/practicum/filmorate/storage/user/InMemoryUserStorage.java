package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
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
    public User findUserById(Integer userId) {
        return users.get(userId);
    }
}
