package ru.yandex.practicum.filmorate.dao.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserStorage {
    int addUser(User user);

    User updateUser(User user);

    Collection<User> findAllUsers();

    User findUserById(Integer id);

    void addFriend(Integer id, Integer friendId);

    void deleteFriend(Integer id, Integer friendId);

    List<Integer> findUserFriends(Integer id);

    List<Integer> findCommonFriends(Integer id, Integer otherId);
}
