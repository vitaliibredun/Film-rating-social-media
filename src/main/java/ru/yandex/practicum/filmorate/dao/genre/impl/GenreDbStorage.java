package ru.yandex.practicum.filmorate.dao.genre.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.genre.GenreStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component("genreDbStorage")
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre findGenre(Integer id) {
        String sql = "select * " +
                     "from genre " +
                     "where id_genre = ?";
        return jdbcTemplate.queryForObject(sql, this::mapToGenre, id);
    }

    @Override
    public Collection<Genre> findAllGenres() {
        String sql = "select * " +
                     "from genre ";
        return jdbcTemplate.query(sql, this::mapToGenre);
    }

    @Override
    public void addFilmToGenre(Film film) {
        String sql = "insert into film_genre (film_id, genre_id) " +
                "values (?, ?)";

        LinkedHashSet<Genre> genres = film.getGenres();
        if (genres == null) {
            return;
        }

        for (Genre genre : genres) {
            Integer genreId = genre.getId();
            Integer filmId = film.getId();
            jdbcTemplate.update(sql, filmId,genreId);
        }
    }

    @Override
    public void deleteGenreFromFilm(Film film) {
        String sql = "delete from film_genre " +
                "where film_id = ?";
        jdbcTemplate.update(sql, film.getId());
    }

    @Override
    public LinkedHashSet<Genre> findFilmGenres(Integer id) {
        String sql = "select genre_id " +
                "from film_genre where film_id = ?";
        List<Integer> integerList = jdbcTemplate.queryForList(sql, Integer.class, id);
        LinkedHashSet<Genre> genres = new LinkedHashSet<>();
        for (Integer integer : integerList) {
            genres.add(findGenre(integer));
        }
        return genres;
    }

    private Genre mapToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("id_genre"))
                .name(resultSet.getString("name_genre"))
                .build();
    }
}
