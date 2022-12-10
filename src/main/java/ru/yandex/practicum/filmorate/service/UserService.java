package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.UserValidation;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;
    private final UserValidation userValidation;

    @Autowired
    public UserService(UserStorage userStorage, UserValidation userValidation) {
        this.userStorage = userStorage;
        this.userValidation = userValidation;
    }

    public User addUser(User user) {
        userValidation.userValidator(user);
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        userValidation.userValidator(user);
        return userStorage.updateUser(user);
    }

    public Collection<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    public User findUserById(Integer userId) {
        return userStorage.findUserById(userId);
    }

    public void addFriend(Integer id, Integer friendId) {
        User userById = userStorage.findUserById(id);
        User friendById = userStorage.findUserById(friendId);
        userById.addFriend(friendId);
        friendById.addFriend(id);
    }

    public void deleteFriend(Integer id,Integer friendId) {
        User userById = userStorage.findUserById(id);
        Set<Integer> friends = userById.getFriendsIds();
        friends.remove(friendId);
    }

    public List<User> findUserFriends(Integer id) {
        List<User> friendsList = new ArrayList<>();
        Set<Integer> friendsIds = userStorage.findUserById(id).getFriendsIds();
        for (Integer friendId : friendsIds) {
            User friendById = userStorage.findUserById(friendId);
            friendsList.add(friendById);
        }

        return friendsList;
    }

    public List<User> findCommonFriends(Integer id, Integer otherId) {
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
