package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dao.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.UserValidation;


import java.util.*;

@Service
public class UserService {
    private final UserStorage userStorage;
    private final UserValidation userValidation;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage, UserValidation userValidation) {
        this.userStorage = userStorage;
        this.userValidation = userValidation;
    }

    public User addUser(User user) {
        userValidation.userValidator(user);
        user.setId(userStorage.addUser(user));
        return user;
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
        userStorage.findUserById(friendId);
        userStorage.addFriend(id, friendId);
    }

    public void deleteFriend(Integer id,Integer friendId) {
        userStorage.deleteFriend(id, friendId);
    }

    public List<User> findUserFriends(Integer id) {
        List<User> friendsList = new ArrayList<>();

        List<Integer> friendsIds = userStorage.findUserFriends(id);
        if (friendsIds == null) {
            return friendsList;
        }
        for (Integer friendId : friendsIds) {
            User friendById = userStorage.findUserById(friendId);
            friendsList.add(friendById);
        }
        return friendsList;
    }

    public List<User> findCommonFriends(Integer id, Integer otherId) {
        List<User> commonFriendsList = new ArrayList<>();

        List<Integer> firstFriendsIds = userStorage.findUserFriends(id);
        if (firstFriendsIds == null) {
            return commonFriendsList;
        }
        List<Integer> commonFriendsIds = userStorage.findCommonFriends(id, otherId);
        for (Integer friendId : commonFriendsIds) {
            User userById = userStorage.findUserById(friendId);
            commonFriendsList.add(userById);
        }
        return commonFriendsList;
    }
}
