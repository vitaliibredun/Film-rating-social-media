package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    private FilmValidation filmValidation;
    private ConfigurableApplicationContext application;

    @BeforeEach
    void setUp() {
        filmValidation = new FilmValidation();
        application = SpringApplication.run(FilmorateApplication.class);
    }

    @AfterEach
    void cleanUp() {
        application.close();
        filmValidation.findAllFilms().clear();
    }

    @Test
    @DisplayName("Проверка на пустое название фильма")
    void addFilmTest1() {
        Film film = new Film();
        film.setName("");
        film.setDescription("Earth's mightiest heroes must come together and learn to fight as a team");
        film.setReleaseDate(LocalDate.of(2012, 4, 26));
        film.setDuration(142);

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> FilmValidation.filmValidator(film));

        Assertions.assertEquals("Название не может быть пустым",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка на максимальную длину описания фильма")
    void addFilmTest2() {
        Film film = new Film();
        film.setName("Avengers");
        film.setDescription("Loki, the adopted brother of Thor, " +
                "teams-up with the Chitauri Army and uses the Tesseract's power " +
                "to travel from Asgard to Midgard to plot the invasion of Earth and " +
                "become a king. The director of the agency S.H.I.E.L.D., Nick Fury, " +
                "sets in motion project Avengers, joining Tony Stark a.k.a. the Iron Man; " +
                "Steve Rogers, a.k.a. Captain America; Bruce Banner, a.k.a. The Hulk; Thor; " +
                "Natasha Romanoff, a.k.a. Black Widow; and Clint Barton, a.k.a. Hawkeye, " +
                "to save the world from the powerful Loki and the alien invasion.");
        film.setReleaseDate(LocalDate.of(2012, 4, 26));
        film.setDuration(142);

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> FilmValidation.filmValidator(film));

        Assertions.assertEquals("Максимальная длина описания — 200 символов",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка на дату релиза")
    void addFilmTest3() {
        Film film = new Film();
        film.setName("Old pictures");
        film.setDescription("It was before commercial films");
        film.setReleaseDate(LocalDate.of(1786, 5, 16));
        film.setDuration(14);

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> FilmValidation.filmValidator(film));

        Assertions.assertEquals("Дата релиза — не раньше 28 декабря 1895 года",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка на положительное значение продолжительности фильма")
    void addFilmTest4() {
        Film film = new Film();
        film.setName("Avengers");
        film.setDescription("Earth's mightiest heroes must come together and learn to fight as a team");
        film.setReleaseDate(LocalDate.of(2012, 4, 26));
        film.setDuration(-142);

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> FilmValidation.filmValidator(film));

        Assertions.assertEquals("Продолжительность фильма должна быть положительной",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка замены фильма с не существующем id")
    void updateFilmTest1() {
        Film film1 = new Film();
        film1.setName("Avengers");
        film1.setDescription("Earth's mightiest heroes must come together and learn to fight as a team");
        film1.setReleaseDate(LocalDate.of(2008, 5, 2));
        film1.setDuration(134);
        filmValidation.addFilm(film1);

        Film film2 = new Film();
        film2.setId(237);
        film2.setName("Iron man");
        film2.setDescription("After being held captive in an Afghan cave, " +
                "billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.");
        film2.setReleaseDate(LocalDate.of(2012, 4, 26));
        film2.setDuration(126);

        final FilmNotFoundException exception = assertThrows(
                FilmNotFoundException.class, () -> FilmValidation.filmValidator(film2));

        Assertions.assertEquals("Фильма с таким id не существует",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка замены фильма с пустым названием")
    void updateFilmTest2() {
        Film film1 = new Film();
        film1.setName("Avengers");
        film1.setDescription("Earth's mightiest heroes must come together and learn to fight as a team");
        film1.setReleaseDate(LocalDate.of(2008, 5, 2));
        film1.setDuration(134);
        filmValidation.addFilm(film1);

        Film film2 = new Film();
        film2.setId(1);
        film2.setName("");
        film2.setDescription("After being held captive in an Afghan cave, " +
                "billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.");
        film2.setReleaseDate(LocalDate.of(2012, 4, 26));
        film2.setDuration(126);

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> FilmValidation.filmValidator(film2));

        Assertions.assertEquals("Название не может быть пустым",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка замены фильма с максимальной длиной описания")
    void updateFilmTest3() {
        Film film1 = new Film();
        film1.setName("Avengers");
        film1.setDescription("Earth's mightiest heroes must come together and learn to fight as a team");
        film1.setReleaseDate(LocalDate.of(2008, 5, 2));
        film1.setDuration(134);
        filmValidation.addFilm(film1);

        Film film2 = new Film();
        film2.setId(1);
        film2.setName("Iron man");
        film2.setDescription("Tony Stark. Genius, billionaire, playboy, philanthropist. " +
                "Son of legendary inventor and weapons contractor Howard Stark. " +
                "When Tony Stark is assigned to give a weapons presentation to " +
                "an Iraqi unit led by Lt. Col. James Rhodes, he's given a ride on enemy lines. " +
                "That ride ends badly when Stark's Humvee that he's riding in is " +
                "attacked by enemy combatants. He survives - barely - with a chest full " +
                "of shrapnel and a car battery attached to his heart. In order to survive " +
                "he comes up with a way to miniaturize the battery and figures out that " +
                "the battery can power something else. Thus Iron Man is born. He uses the " +
                "primitive device to escape from the cave in Iraq. Once back home, he then " +
                "begins work on perfecting the Iron Man suit. But the man who was put in charge " +
                "of Stark Industries has plans of his own to take over Tony's technology for other matters.");
        film2.setReleaseDate(LocalDate.of(2012, 4, 26));
        film2.setDuration(126);

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> FilmValidation.filmValidator(film2));

        Assertions.assertEquals("Максимальная длина описания — 200 символов",exception.getMessage());
    }


    @Test
    @DisplayName("Проверка замены фильма по дате релиза")
    void updateFilmTest4() {
        Film film1 = new Film();
        film1.setName("Avengers");
        film1.setDescription("Earth's mightiest heroes must come together and learn to fight as a team");
        film1.setReleaseDate(LocalDate.of(2008, 5, 2));
        film1.setDuration(134);
        filmValidation.addFilm(film1);

        Film film2 = new Film();
        film2.setId(1);
        film2.setName("Old pictures");
        film2.setDescription("It was before commercial films");
        film2.setReleaseDate(LocalDate.of(1786, 5, 16));
        film2.setDuration(14);

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> FilmValidation.filmValidator(film2));

        Assertions.assertEquals("Дата релиза — не раньше 28 декабря 1895 года",exception.getMessage());
    }

    @Test
    @DisplayName("Проверка замены фильма по положительному значению продолжительности")
    void updateFilmTest5() {
        Film film1 = new Film();
        film1.setName("Avengers");
        film1.setDescription("Earth's mightiest heroes must come together and learn to fight as a team");
        film1.setReleaseDate(LocalDate.of(2008, 5, 2));
        film1.setDuration(134);
        filmValidation.addFilm(film1);

        Film film2 = new Film();
        film2.setId(1);
        film2.setName("Iron man");
        film2.setDescription("After being held captive in an Afghan cave, " +
                "billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.");
        film2.setReleaseDate(LocalDate.of(2012, 4, 26));
        film2.setDuration(-126);

        final ValidationException exception = assertThrows(
                ValidationException.class, () -> FilmValidation.filmValidator(film2));

        Assertions.assertEquals("Продолжительность фильма должна быть положительной",exception.getMessage());
    }
}