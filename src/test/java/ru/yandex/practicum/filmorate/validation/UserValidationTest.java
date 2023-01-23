package ru.yandex.practicum.filmorate.validation;

import org.junit.jupiter.api.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dao.user.impl.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.dao.user.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserValidationTest {
    private UserValidation userValidation;
    private ConfigurableApplicationContext application;
    private UserStorage userStorage;

    @BeforeEach
    void setUp() {
        userValidation = new UserValidation();
        userStorage = new InMemoryUserStorage();
        application = SpringApplication.run(FilmorateApplication.class);
    }

    @AfterEach
    void cleanUp() {
        application.close();
    }

    @Test
    @DisplayName("Проверка добавления user с пустым значением email почты")
    void addUserTest1() {
        User user = User.builder()
                .login("login")
                .name("Nick Name")
                .email("")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.userValidator(user));

        Assertions.assertEquals("Электронная почта не может быть пустой и должна содержать символ @",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка добавления user с отсутсвием символа @ в значении почты")
    void addUserTest2() {
        User user = User.builder()
                .login("login")
                .name("NickName")
                .email("mail.com")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.userValidator(user));

        Assertions.assertEquals("Электронная почта не может быть пустой и должна содержать символ @",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка добавления user с отсутсвием логина")
    void addUserTest3() {
        User user = User.builder()
                .login("")
                .name("NickName")
                .email("mail@email.com")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.userValidator(user));

        Assertions.assertEquals("Логин не может быть пустым и содержать пробелы",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка добавления user с пробелами в логине")
    void addUserTest4() {
        User user = User.builder()
                .login("log in")
                .name("NickName")
                .email("mail@email.com")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.userValidator(user));

        Assertions.assertEquals("Логин не может быть пустым и содержать пробелы",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка добавления user с датой рождения будущего времени")
    void addUserTest5() {
        User user = User.builder()
                .login("login")
                .name("NickName")
                .email("mail@email.com")
                .birthday(LocalDate.of(2045, 12, 5))
                .build();

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.userValidator(user));

        Assertions.assertEquals("Дата рождения не может быть в будущем",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка замены user с пустым значением email почты")
    void updateUserTest1() {
        User user1 = User.builder()
                .login("login")
                .name("NickName")
                .email("mail@email.com")
                .birthday(LocalDate.of(1980, 12, 15))
                .build();
        userStorage.addUser(user1);

        User user2 = User.builder()
                .id(1)
                .login("loginer")
                .name("BobName")
                .email("")
                .birthday(LocalDate.of(1990, 11, 5))
                .build();

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.userValidator(user2));

        Assertions.assertEquals("Электронная почта не может быть пустой и должна содержать символ @",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка замены user с отсутсвием символа @ в значении почты")
    void updateUserTest2() {
        User user1 = User.builder()
                .login("login")
                .name("NickName")
                .email("mail@email.com")
                .birthday(LocalDate.of(1980, 12, 15))
                .build();
        userStorage.addUser(user1);

        User user2 = User.builder()
                .id(1)
                .login("loginer")
                .name("BobName")
                .email("newMail.ru")
                .birthday(LocalDate.of(1990, 11, 5))
                .build();

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.userValidator(user2));

        Assertions.assertEquals("Электронная почта не может быть пустой и должна содержать символ @",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка замены user с отсутсвием логина")
    void updateUserTest3() {
        User user1 = User.builder()
                .login("login")
                .name("NickName")
                .email("mail@email.com")
                .birthday(LocalDate.of(1980, 12, 15))
                .build();
        userStorage.addUser(user1);

        User user2 = User.builder()
                .id(1)
                .login("")
                .name("BobName")
                .email("mail@email.com")
                .birthday(LocalDate.of(1990, 11, 5))
                .build();

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.userValidator(user2));

        Assertions.assertEquals("Логин не может быть пустым и содержать пробелы",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка замены user с пробелами в логине")
    void updateUserTest4() {
        User user1 = User.builder()
                .login("login")
                .name("NickName")
                .email("mail@email.com")
                .birthday(LocalDate.of(1980, 12, 15))
                .build();
        userStorage.addUser(user1);

        User user2 = User.builder()
                .id(1)
                .login("logi ner")
                .name("BobName")
                .email("mail@email.com")
                .birthday(LocalDate.of(1990, 11, 5))
                .build();

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.userValidator(user2));

        Assertions.assertEquals("Логин не может быть пустым и содержать пробелы",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка замены user с датой рождения будущего времени")
    void updateUserTest5() {
        User user1 = User.builder()
                .login("login")
                .name("NickName")
                .email("mail@email.com")
                .birthday(LocalDate.of(1980, 12, 15))
                .build();
        userStorage.addUser(user1);

        User user2 = User.builder()
                .id(1)
                .login("loginer")
                .name("BobName")
                .email("mail@email.com")
                .birthday(LocalDate.of(2390, 11, 5))
                .build();

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> userValidation.userValidator(user2));

        Assertions.assertEquals("Дата рождения не может быть в будущем", exception.getMessage());
    }
}

