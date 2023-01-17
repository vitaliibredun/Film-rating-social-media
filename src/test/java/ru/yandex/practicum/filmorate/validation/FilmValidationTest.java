package ru.yandex.practicum.filmorate.validation;

import org.junit.jupiter.api.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.dao.film.FilmStorage;
import ru.yandex.practicum.filmorate.dao.film.impl.InMemoryFilmStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmValidationTest {
    private FilmValidation filmValidation;
    private ConfigurableApplicationContext application;
    private FilmStorage filmStorage;

    @BeforeEach
    void setUp() {
        filmValidation = new FilmValidation();
        filmStorage = new InMemoryFilmStorage();
        application = SpringApplication.run(FilmorateApplication.class);
    }

    @AfterEach
    void cleanUp() {
        application.close();
    }

    @Test
    @DisplayName("Проверка на пустое название фильма")
    void addFilmTest1() {
        Film film = Film.builder()
                .name("")
                .description("Earth's mightiest heroes must come together and learn to fight as a team")
                .releaseDate(LocalDate.of(2012, 4, 26))
                .duration(142)
                .build();

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> filmValidation.filmValidator(film));

        Assertions.assertEquals("Название не может быть пустым",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка на максимальную длину описания фильма")
    void addFilmTest2() {
        Film film = Film.builder()
                .name("Avengers")
                .description("Loki, the adopted brother of Thor, \" +\n" +
                        "                \"teams-up with the Chitauri Army and uses the Tesseract's power \" +\n" +
                        "                \"to travel from Asgard to Midgard to plot the invasion of Earth and \" +\n" +
                        "                \"become a king. The director of the agency S.H.I.E.L.D., Nick Fury, \" +\n" +
                        "                \"sets in motion project Avengers, joining Tony Stark a.k.a. the Iron Man; \" +\n" +
                        "                \"Steve Rogers, a.k.a. Captain America; Bruce Banner, a.k.a. The Hulk; Thor; \" +\n" +
                        "                \"Natasha Romanoff, a.k.a. Black Widow; and Clint Barton, a.k.a. Hawkeye, \" +\n" +
                        "                \"to save the world from the powerful Loki and the alien invasion.")
                .releaseDate(LocalDate.of(2012, 4, 26))
                .duration(142)
                .build();

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> filmValidation.filmValidator(film));

        Assertions.assertEquals("Максимальная длина описания — 200 символов",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка на дату релиза")
    void addFilmTest3() {
        Film film = Film.builder()
                .name("Old pictures")
                .description("It was before commercial films")
                .releaseDate(LocalDate.of(1786, 5, 16))
                .duration(14)
                .build();

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> filmValidation.filmValidator(film));

        Assertions.assertEquals("Дата релиза — не раньше 28 декабря 1895 года",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка на положительное значение продолжительности фильма")
    void addFilmTest4() {
        Film film = Film.builder()
                .name("Avengers")
                .description("Earth's mightiest heroes must come together and learn to fight as a team")
                .releaseDate(LocalDate.of(2012, 4, 26))
                .duration(-142)
                .build();

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> filmValidation.filmValidator(film));

        Assertions.assertEquals("Продолжительность фильма должна быть положительной",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка замены фильма с пустым названием")
    void updateFilmTest1() {
        Film film1 = Film.builder()
                .name("Avengers")
                .description("Earth's mightiest heroes must come together and learn to fight as a team")
                .releaseDate(LocalDate.of(2012, 4, 26))
                .duration(134)
                .build();

        filmStorage.addFilm(film1);

        Film film2 = Film.builder()
                .id(2)
                .name("")
                .description("After being held captive in an Afghan cave, \" +\n" +
                        "                \"billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.")
                .releaseDate(LocalDate.of(2012, 4, 26))
                .duration(126)
                .build();

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> filmValidation.filmValidator(film2));

        Assertions.assertEquals("Название не может быть пустым", exception.getMessage());
    }

    @Test
    @DisplayName("Проверка замены фильма с максимальной длиной описания")
    void updateFilmTest2() {
        Film film1 = Film.builder()
                .name("Avengers")
                .description("Earth's mightiest heroes must come together and learn to fight as a team")
                .releaseDate(LocalDate.of(2012, 4, 26))
                .duration(134)
                .build();

        filmStorage.addFilm(film1);

        Film film2 = Film.builder()
                .id(1)
                .name("Iron man")
                .description("Tony Stark. Genius, billionaire, playboy, philanthropist. \" +\n" +
                        "                \"Son of legendary inventor and weapons contractor Howard Stark. \" +\n" +
                        "                \"When Tony Stark is assigned to give a weapons presentation to \" +\n" +
                        "                \"an Iraqi unit led by Lt. Col. James Rhodes, he's given a ride on enemy lines. \" +\n" +
                        "                \"That ride ends badly when Stark's Humvee that he's riding in is \" +\n" +
                        "                \"attacked by enemy combatants. He survives - barely - with a chest full \" +\n" +
                        "                \"of shrapnel and a car battery attached to his heart. In order to survive \" +\n" +
                        "                \"he comes up with a way to miniaturize the battery and figures out that \" +\n" +
                        "                \"the battery can power something else. Thus Iron Man is born. He uses the \" +\n" +
                        "                \"primitive device to escape from the cave in Iraq. Once back home, he then \" +\n" +
                        "                \"begins work on perfecting the Iron Man suit. But the man who was put in charge \" +\n" +
                        "                \"of Stark Industries has plans of his own to take over Tony's technology for other matters.")
                .releaseDate(LocalDate.of(2012, 4, 26))
                .duration(126)
                .build();

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> filmValidation.filmValidator(film2));

        Assertions.assertEquals("Максимальная длина описания — 200 символов",exception.getMessage());
    }


    @Test
    @DisplayName("Проверка замены фильма по дате релиза")
    void updateFilmTest3() {
        Film film1 = Film.builder()
                .name("Avengers")
                .description("Earth's mightiest heroes must come together and learn to fight as a team")
                .releaseDate(LocalDate.of(2012, 4, 26))
                .duration(134)
                .build();

        filmStorage.addFilm(film1);

        Film film2 = Film.builder()
                .id(1)
                .name("Old pictures")
                .description("It was before commercial films")
                .releaseDate(LocalDate.of(1786, 5, 16))
                .duration(14)
                .build();

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> filmValidation.filmValidator(film2));

        Assertions.assertEquals("Дата релиза — не раньше 28 декабря 1895 года",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка замены фильма по положительному значению продолжительности")
    void updateFilmTest4() {
        Film film1 = Film.builder()
                .name("Avengers")
                .description("Earth's mightiest heroes must come together and learn to fight as a team")
                .releaseDate(LocalDate.of(2008, 5, 2))
                .duration(134)
                .build();

        filmStorage.addFilm(film1);

        Film film2 = Film.builder()
                .id(1)
                .name("Iron man")
                .description("After being held captive in an Afghan cave, \" +\n" +
                        "                \"billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.")
                .releaseDate(LocalDate.of(2008, 5, 2))
                .duration(-126)
                .build();


        final ValidationException exception = assertThrows(
                ValidationException.class, () -> filmValidation.filmValidator(film2));

        Assertions.assertEquals("Продолжительность фильма должна быть положительной",exception.getMessage());
    }
}