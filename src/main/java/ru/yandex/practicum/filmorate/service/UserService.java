package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;


import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.validation.UserValidation.userValidator;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addUser(User user) {
        if (userStorage.findAllUsers().contains(user)) {
            throw new UserAlreadyExistException("The user is already exist");
        }

        userValidator(user);
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        if (userStorage.findAllUsers().contains(user)) {
            throw new UserAlreadyExistException("The user is already exist");
        }

        userValidator(user);
        return userStorage.updateUser(user);
    }

    public Collection<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    public User findUserById(int id) {
        return userStorage.findUserById(id);
    }

    public void addFriend(int id, int friendId) {
        if (!userStorage.findAllUsersIds().contains(friendId)) {
            throw new UserNotFoundException("The user not found");
        }
        User userById = userStorage.findUserById(id);
        User friendById = userStorage.findUserById(friendId);
        userById.addFriend(friendId);
        friendById.addFriend(id);
    }

    public void deleteFriend(int id,int friendId) {
        User userById = userStorage.findUserById(id);
        Set<Integer> friends = userById.getFriendsIds();
        friends.remove(friendId);
    }

    public List<User> findUserFriends(int id) {
        List<User> friendsList = new ArrayList<>();
        Set<Integer> friendsIds = userStorage.findUserById(id).getFriendsIds();
        for (Integer friendId : friendsIds) {
            User friendById = userStorage.findUserById(friendId);
            friendsList.add(friendById);
        }

        return friendsList;
    }

    public List<User> findCommonFriends(int id, int otherId) {
        List<User> commonFriendsList = new ArrayList<>();
        Set<Integer> firstFriendsIds = userStorage.findUserById(id).getFriendsIds();
        Set<Integer> secondFriendsIds = userStorage.findUserById(otherId).getFriendsIds();
        List<Integer> commonFriendsIds = firstFriendsIds.stream()
                .filter(second -> secondFriendsIds.stream()
                        .anyMatch(first -> first.equals(second))).collect(Collectors.toList());
        for (Integer friendId : commonFriendsIds) {
            User userById = userStorage.findUserById(friendId);
            commonFriendsList.add(userById);
        }

        return commonFriendsList;
    }
}
