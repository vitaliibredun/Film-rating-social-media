package ru.yandex.practicum.filmorate.dao.user.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.user.UserStorage;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component("inMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private Integer counter = 0;
    private Integer setInId() {
        return ++counter;
    }

    @Override
    public int addUser(User user) {
        if (users.containsValue(user)) {
            throw new UserAlreadyExistException("The user is already exists");
        }
        user.setId(setInId());
        users.put(counter, user);
        return user.getId();
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

    @Override
    public void addFriend(Integer id, Integer friendId) {
        User userById = findUserById(id);
        User friendById = findUserById(friendId);
        userById.addFriend(friendId);
        friendById.addFriend(id);
    }

    @Override
    public void deleteFriend(Integer id,Integer friendId) {
        User userById = findUserById(id);
        Set<Integer> friends = userById.getFriendsIds();
        friends.remove(friendId);
    }

    @Override
    public List<Integer> findUserFriends(Integer id) {
        User userById = findUserById(id);
        Set<Integer> friendsIds = userById.getFriendsIds();
        return new ArrayList<>(friendsIds);
    }

    @Override
    public List<Integer> findCommonFriends(Integer id, Integer otherId) {
        List<Integer> firstFriendsIds = findUserFriends(id);
        List<Integer> secondFriendsIds = findUserFriends(otherId);

        return firstFriendsIds.stream()
                .filter(second -> secondFriendsIds.stream()
                        .anyMatch(first -> first.equals(second))).collect(Collectors.toList());
    }
}
