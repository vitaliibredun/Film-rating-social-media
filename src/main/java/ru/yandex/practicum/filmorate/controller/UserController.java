package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.*;


@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        log.debug("User: {}", user);
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        log.debug("User: {}", user);
        return userService.updateUser(user);
    }

    @GetMapping
    public Collection<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public User findUserById(@PathVariable Integer userId) {
        return userService.findUserById(userId);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable Integer userId, @PathVariable Integer friendId) {
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void deleteFriend(@PathVariable Integer userId, @PathVariable Integer friendId) {
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public List<User> findUserFriends(@PathVariable Integer userId) {
        return userService.findUserFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public List<User> findCommonFriends(@PathVariable Integer userId, @PathVariable Integer otherId) {
        return userService.findCommonFriends(userId, otherId);
    }
}
