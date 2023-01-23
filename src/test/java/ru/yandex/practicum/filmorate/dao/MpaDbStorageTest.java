package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.dao.mpa.MpaStorage;

@SpringBootTest
@AutoConfigureTestDatabase
public class MpaDbStorageTest {
    @Autowired
    private MpaStorage mpaStorage;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void cleanUp() {
        jdbcTemplate.execute("DELETE FROM films");
        jdbcTemplate.execute("ALTER TABLE films ALTER COLUMN id RESTART WITH 1");
    }

    @Test
    @DisplayName("Проверка поиска MPA по id")
    void findMpaTest() {
        Assertions.assertAll(
                () -> Assertions.assertEquals("G", mpaStorage.findMpa(1).getName(), "Данные не верны"),
                () -> Assertions.assertEquals("PG", mpaStorage.findMpa(2).getName(), "Данные не верны")
        );
    }

    @Test
    @DisplayName("Проверка поиска всех MPA")
    void findAllMpa() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(5, mpaStorage.findAllMpa().size(), "Данные не верны"),
                () -> Assertions.assertEquals("G", mpaStorage.findMpa(1).getName(), "Данные не верны"),
                () -> Assertions.assertEquals("PG", mpaStorage.findMpa(2).getName(), "Данные не верны"),
                () -> Assertions.assertEquals("PG-13", mpaStorage.findMpa(3).getName(), "Данные не верны"),
                () -> Assertions.assertEquals("R", mpaStorage.findMpa(4).getName(), "Данные не верны"),
                () -> Assertions.assertEquals("NC-17", mpaStorage.findMpa(5).getName(), "Данные не верны")
        );
    }
}
