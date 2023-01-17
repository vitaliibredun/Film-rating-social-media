package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.dao.film.FilmStorage;
import ru.yandex.practicum.filmorate.dao.user.UserStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
public class FilmDbStorageTest {
    @Qualifier("filmDbStorage")
    @Autowired
    private FilmStorage filmStorage;
    @Qualifier("userDbStorage")
    @Autowired
    private UserStorage userStorage;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void cleanUp() {
        jdbcTemplate.execute("DELETE FROM films");
        jdbcTemplate.execute("ALTER TABLE films ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("DELETE FROM users");
        jdbcTemplate.execute("ALTER TABLE users ALTER COLUMN id RESTART WITH 1");
    }

    @Test
    @DisplayName("Проверка добавления фильма")
    void addFilmTest() {
        Film filmExpected = Film.builder()
                .id(1)
                .name("Iron Man")
                .description("Billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.")
                .releaseDate(LocalDate.of(2008, 5, 2))
                .duration(126)
                .mpa(Mpa.builder().id(1).name("G").build())
                .genres(new LinkedHashSet<>())
                .build();

        Film film = Film.builder()
                .name("Iron Man")
                .description("Billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.")
                .releaseDate(LocalDate.of(2008, 5, 2))
                .duration(126)
                .mpa(Mpa.builder().id(1).name("G").build())
                .build();

        filmStorage.addFilm(film);

        Film filmFromDb = filmStorage.findFilmById(1);

        Assertions.assertEquals(filmExpected, filmFromDb, "Данные не совпадают");
    }

    @Test
    @DisplayName("Проверка обновления фильма")
    void updateFilmTest() {
        Film filmExpected = Film.builder()
                .id(1)
                .name("Iron Man")
                .description("Billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.")
                .releaseDate(LocalDate.of(2008, 5, 2))
                .duration(126)
                .mpa(Mpa.builder().id(1).name("G").build())
                .genres(new LinkedHashSet<>())
                .build();

        Film film = Film.builder()
                .name("Spider-Man: Homecoming")
                .description("Peter Parker balances his life as an ordinary high school " +
                        "student in Queens with his superhero alter-ego Spider-Man.")
                .releaseDate(LocalDate.of(2017, 7, 5))
                .duration(133)
                .mpa(Mpa.builder().id(1).name("G").build())
                .genres(new LinkedHashSet<>())
                .build();

        filmStorage.addFilm(film);

        filmStorage.updateFilm(filmExpected);

        Film filmFromDb = filmStorage.findFilmById(1);

        Assertions.assertEquals(filmExpected, filmFromDb, "Данные не совпадают");
    }

    @Test
    @DisplayName("Проверка вызова всех фильмов")
    void findAllFilmsTest() {
        Assertions.assertEquals(0, filmStorage.findAllFilms().size());

        Film filmExpected1 = Film.builder()
                .id(1)
                .name("Iron Man")
                .description("Billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.")
                .releaseDate(LocalDate.of(2008, 5, 2))
                .duration(126)
                .mpa(Mpa.builder().id(1).name("G").build())
                .genres(new LinkedHashSet<>())
                .build();

        Film filmExpected2 = Film.builder()
                .id(2)
                .name("Spider-Man: Homecoming")
                .description("Peter Parker balances his life as an ordinary high school " +
                        "student in Queens with his superhero alter-ego Spider-Man.")
                .releaseDate(LocalDate.of(2017, 7, 5))
                .duration(133)
                .mpa(Mpa.builder().id(1).name("G").build())
                .genres(new LinkedHashSet<>())
                .build();

        Film film1 = Film.builder()
                .name("Iron Man")
                .description("Billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.")
                .releaseDate(LocalDate.of(2008, 5, 2))
                .duration(126)
                .mpa(Mpa.builder().id(1).name("G").build())
                .genres(new LinkedHashSet<>())
                .build();

        Film film2 = Film.builder()
                .name("Spider-Man: Homecoming")
                .description("Peter Parker balances his life as an ordinary high school " +
                        "student in Queens with his superhero alter-ego Spider-Man.")
                .releaseDate(LocalDate.of(2017, 7, 5))
                .duration(133)
                .mpa(Mpa.builder().id(1).name("G").build())
                .genres(new LinkedHashSet<>())
                .build();

        filmStorage.addFilm(film1);
        filmStorage.addFilm(film2);

        Film filmFromDb1 = filmStorage.findFilmById(1);
        Film filmFromDb2 = filmStorage.findFilmById(2);

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, filmStorage.findAllFilms().size(), "Данные не верны"),
                () -> Assertions.assertEquals(filmExpected1, filmFromDb1, "Данные не совпадают"),
                () -> Assertions.assertEquals(filmExpected2, filmFromDb2, "Данные не совпадают")
        );
    }

    @Test
    @DisplayName("Проверка вызова фильма по id")
    void findFilmByIdTest() {
        Assertions.assertEquals(0, filmStorage.findAllFilms().size(), "Данные не верны");

        Film filmExpected = Film.builder()
                .id(1)
                .name("Iron Man")
                .description("Billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.")
                .releaseDate(LocalDate.of(2008, 5, 2))
                .duration(126)
                .mpa(Mpa.builder().id(1).name("G").build())
                .genres(new LinkedHashSet<>())
                .build();

        Film film = Film.builder()
                .name("Iron Man")
                .description("Billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.")
                .releaseDate(LocalDate.of(2008, 5, 2))
                .duration(126)
                .mpa(Mpa.builder().id(1).name("G").build())
                .genres(new LinkedHashSet<>())
                .build();

        filmStorage.addFilm(film);

        Film filmFromDb = filmStorage.findFilmById(1);

        Assertions.assertEquals(filmExpected, filmFromDb, "Данные не совпадают");
    }

    @Test
    @DisplayName("Проверка добавления рейтинга фильму")
    void addRateToFilmTest() {
        Film film1 = Film.builder()
                .id(1)
                .name("Iron Man")
                .description("Billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.")
                .releaseDate(LocalDate.of(2008, 5, 2))
                .duration(126)
                .mpa(Mpa.builder().id(1).name("G").build())
                .genres(new LinkedHashSet<>())
                .build();

        Film film2 = Film.builder()
                .id(2)
                .name("Spider-Man: Homecoming")
                .description("Peter Parker balances his life as an ordinary high school " +
                        "student in Queens with his superhero alter-ego Spider-Man.")
                .releaseDate(LocalDate.of(2017, 7, 5))
                .duration(133)
                .mpa(Mpa.builder().id(1).name("G").build())
                .genres(new LinkedHashSet<>())
                .build();

        User user = User.builder()
                .id(1)
                .login("stark")
                .name("Tony")
                .email("tony@gmail.com")
                .birthday(LocalDate.of(1965, 4, 4))
                .build();
        userStorage.addUser(user);
        filmStorage.addFilm(film1);
        filmStorage.addFilm(film2);

        List<Film> filmsByRate1 = filmStorage.findFilmsByRate(10);

        Assertions.assertAll(
                () -> Assertions.assertEquals(film1, filmsByRate1.get(0), "Данные не верны"),
                () -> Assertions.assertEquals(film2, filmsByRate1.get(1), "Данные не верны")
        );

        filmStorage.addRateToFilm(film2.getId(), user.getId());

        List<Film> filmsByRate2 = filmStorage.findFilmsByRate(10);

        Assertions.assertAll(
                () -> Assertions.assertEquals(film2, filmsByRate2.get(0), "Данные не верны"),
                () -> Assertions.assertEquals(film1, filmsByRate2.get(1), "Данные не верны")
        );
    }

    @Test
    @DisplayName("роверка удаления рейтинга фильму")
    void deleteRateFromFilmTest() {
        Film film1 = Film.builder()
                .id(1)
                .name("Iron Man")
                .description("Billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.")
                .releaseDate(LocalDate.of(2008, 5, 2))
                .duration(126)
                .mpa(Mpa.builder().id(1).name("G").build())
                .genres(new LinkedHashSet<>())
                .build();

        Film film2 = Film.builder()
                .id(2)
                .name("Spider-Man: Homecoming")
                .description("Peter Parker balances his life as an ordinary high school " +
                        "student in Queens with his superhero alter-ego Spider-Man.")
                .releaseDate(LocalDate.of(2017, 7, 5))
                .duration(133)
                .mpa(Mpa.builder().id(1).name("G").build())
                .genres(new LinkedHashSet<>())
                .build();

        User user = User.builder()
                .id(1)
                .login("stark")
                .name("Tony")
                .email("tony@gmail.com")
                .birthday(LocalDate.of(1965, 4, 4))
                .build();
        userStorage.addUser(user);
        filmStorage.addFilm(film1);
        filmStorage.addFilm(film2);
        filmStorage.addRateToFilm(film2.getId(), user.getId());

        List<Film> filmsByRate1 = filmStorage.findFilmsByRate(10);

        Assertions.assertAll(
                () -> Assertions.assertEquals(film2, filmsByRate1.get(0), "Данные не верны"),
                () -> Assertions.assertEquals(film1, filmsByRate1.get(1), "Данные не верны")
        );

        filmStorage.deleteRateFromFilm(film2.getId(), user.getId());

        List<Film> filmsByRate2 = filmStorage.findFilmsByRate(10);

        Assertions.assertAll(
                () -> Assertions.assertEquals(film1, filmsByRate2.get(0), "Данные не верны"),
                () -> Assertions.assertEquals(film2, filmsByRate2.get(1), "Данные не верны")
        );
    }

    @Test
    @DisplayName("Проверка добавления рейтинга фильму")
    void findFilmsByRateTest() {
        Film film1 = Film.builder()
                .id(1)
                .name("Iron Man")
                .description("Billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.")
                .releaseDate(LocalDate.of(2008, 5, 2))
                .duration(126)
                .mpa(Mpa.builder().id(1).name("G").build())
                .genres(new LinkedHashSet<>())
                .build();

        Film film2 = Film.builder()
                .id(2)
                .name("Spider-Man: Homecoming")
                .description("Peter Parker balances his life as an ordinary high school " +
                        "student in Queens with his superhero alter-ego Spider-Man.")
                .releaseDate(LocalDate.of(2017, 7, 5))
                .duration(133)
                .mpa(Mpa.builder().id(1).name("G").build())
                .genres(new LinkedHashSet<>())
                .build();

        User user = User.builder()
                .id(1)
                .login("stark")
                .name("Tony")
                .email("tony@gmail.com")
                .birthday(LocalDate.of(1965, 4, 4))
                .build();
        userStorage.addUser(user);
        filmStorage.addFilm(film1);
        filmStorage.addFilm(film2);

        List<Film> filmsByRate1 = filmStorage.findFilmsByRate(10);

        Assertions.assertAll(
                () -> Assertions.assertEquals(film1, filmsByRate1.get(0), "Данные не верны"),
                () -> Assertions.assertEquals(film2, filmsByRate1.get(1), "Данные не верны")
        );

        filmStorage.addRateToFilm(film2.getId(), user.getId());

        List<Film> filmsByRate2 = filmStorage.findFilmsByRate(10);

        Assertions.assertEquals(film2, filmsByRate2.get(0), "Данные не верны");
    }
}
