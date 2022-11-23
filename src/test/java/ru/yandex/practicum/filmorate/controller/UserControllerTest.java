package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.UserValidation;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private UserValidation userValidation;
    private ConfigurableApplicationContext application;

    @BeforeEach
    void setUp() {
        userValidation = new UserValidation();
        application = SpringApplication.run(FilmorateApplication.class);
    }

    @AfterEach
    void cleanUp() {
        application.close();
        userValidation.findAllUsers().clear();
    }

    @Test
    @DisplayName("Проверка добавления user с пустым значением email почты")
    void addUserTest1() {
        User user = new User();
        user.setLogin("login");
        user.setName("Nick Name");
        user.setEmail("");
        user.setBirthday(LocalDate.of(1946, 8, 20));

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.addUser(user));

        Assertions.assertEquals("Электронная почта не может быть пустой и должна содержать символ @",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка добавления user с отсутсвием символа @ в значении почты")
    void addUserTest2() {
        User user = new User();
        user.setLogin("login");
        user.setName("NickName");
        user.setEmail("mail.com");
        user.setBirthday(LocalDate.of(1946, 8, 20));

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.addUser(user));

        Assertions.assertEquals("Электронная почта не может быть пустой и должна содержать символ @",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка добавления user с отсутсвием логина")
    void addUserTest3() {
        User user = new User();
        user.setLogin("");
        user.setName("NickName");
        user.setEmail("mail@email.com");
        user.setBirthday(LocalDate.of(1946, 8, 20));

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.addUser(user));

        Assertions.assertEquals("Логин не может быть пустым и содержать пробелы",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка добавления user с пробелами в логине")
    void addUserTest4() {
        User user = new User();
        user.setLogin("log in");
        user.setName("NickName");
        user.setEmail("mail@email.com");
        user.setBirthday(LocalDate.of(1946, 8, 20));

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.addUser(user));

        Assertions.assertEquals("Логин не может быть пустым и содержать пробелы",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка добавления user с датой рождения будущего времени")
    void addUserTest5() {
        User user = new User();
        user.setLogin("login");
        user.setName("NickName");
        user.setEmail("mail@email.com");
        user.setBirthday(LocalDate.of(2045, 12, 5));

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.addUser(user));

        Assertions.assertEquals("Дата рождения не может быть в будущем",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка замены user с не существующем id")
    void updateUserTest1() {
        User user1 = new User();
        user1.setLogin("login");
        user1.setName("NickName");
        user1.setEmail("mail@email.com");
        user1.setBirthday(LocalDate.of(1980, 12, 15));
        userValidation.addUser(user1);

        User user2 = new User();
        user2.setId(234);
        user2.setLogin("loginer");
        user2.setName("BobName");
        user2.setEmail("new@email.ru");
        user2.setBirthday(LocalDate.of(1990, 11, 5));

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.updateUser(user2));

        Assertions.assertEquals("Пользователя с таким id не существует",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка замены user с пустым значением email почты")
    void updateUserTest2() {
        User user1 = new User();
        user1.setLogin("login");
        user1.setName("NickName");
        user1.setEmail("mail@email.com");
        user1.setBirthday(LocalDate.of(1980, 12, 15));
        userValidation.addUser(user1);

        User user2 = new User();
        user2.setId(1);
        user2.setLogin("loginer");
        user2.setName("BobName");
        user2.setEmail("");
        user2.setBirthday(LocalDate.of(1990, 11, 5));

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.updateUser(user2));

        Assertions.assertEquals("Электронная почта не может быть пустой и должна содержать символ @",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка замены user с отсутсвием символа @ в значении почты")
    void updateUserTest3() {
        User user1 = new User();
        user1.setLogin("login");
        user1.setName("NickName");
        user1.setEmail("mail@email.com");
        user1.setBirthday(LocalDate.of(1980, 12, 15));
        userValidation.addUser(user1);

        User user2 = new User();
        user2.setId(1);
        user2.setLogin("loginer");
        user2.setName("BobName");
        user2.setEmail("newMail.ru");
        user2.setBirthday(LocalDate.of(1990, 11, 5));

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.updateUser(user2));

        Assertions.assertEquals("Электронная почта не может быть пустой и должна содержать символ @",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка замены user с отсутсвием логина")
    void updateUserTest4() {
        User user1 = new User();
        user1.setLogin("login");
        user1.setName("NickName");
        user1.setEmail("mail@email.com");
        user1.setBirthday(LocalDate.of(1980, 12, 15));
        userValidation.addUser(user1);

        User user2 = new User();
        user2.setId(1);
        user2.setLogin("");
        user2.setName("BobName");
        user2.setEmail("mail@email.com");
        user2.setBirthday(LocalDate.of(1990, 11, 5));

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.updateUser(user2));

        Assertions.assertEquals("Логин не может быть пустым и содержать пробелы",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка замены user с пробелами в логине")
    void updateUserTest5() {
        User user1 = new User();
        user1.setLogin("login");
        user1.setName("NickName");
        user1.setEmail("mail@email.com");
        user1.setBirthday(LocalDate.of(1980, 12, 15));
        userValidation.addUser(user1);

        User user2 = new User();
        user2.setId(1);
        user2.setLogin("logi ner");
        user2.setName("BobName");
        user2.setEmail("mail@email.com");
        user2.setBirthday(LocalDate.of(1990, 11, 5));

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.updateUser(user2));

        Assertions.assertEquals("Логин не может быть пустым и содержать пробелы",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка замены user с датой рождения будущего времени")
    void updateUserTest6() {
        User user1 = new User();
        user1.setLogin("login");
        user1.setName("NickName");
        user1.setEmail("mail@email.com");
        user1.setBirthday(LocalDate.of(1980, 12, 15));
        userValidation.addUser(user1);

        User user2 = new User();
        user2.setId(1);
        user2.setLogin("loginer");
        user2.setName("BobName");
        user2.setEmail("mail@email.com");
        user2.setBirthday(LocalDate.of(2390, 11, 5));

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.updateUser(user2));

        Assertions.assertEquals("Дата рождения не может быть в будущем", exception.getMessage());
    }
}

