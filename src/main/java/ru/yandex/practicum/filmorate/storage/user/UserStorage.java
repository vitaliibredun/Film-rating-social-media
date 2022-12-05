package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserStorage {
    User addUser(User user);

    User updateUser(User user);

    Collection<User> findAllUsers();

    User findUserById(int id);

    Set<Integer> findAllUsersIds();
}
