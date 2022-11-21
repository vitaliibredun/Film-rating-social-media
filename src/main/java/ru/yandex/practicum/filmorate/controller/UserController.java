package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

import static ru.yandex.practicum.filmorate.validation.UserValidation.userValidator;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    protected static final Map<Integer, User> users = new HashMap<>();
    private int counter = 0;

    @GetMapping
    public Collection<User> findAllUsers() {
        return users.values();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.debug("User: {}", user);
        userValidator(user);
        user.setId(++counter);
        users.put(counter,user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.debug("User: {}", user);
        int id = user.getId();
        userValidator(user);
        users.put(id, user);
        return user;
    }
}
