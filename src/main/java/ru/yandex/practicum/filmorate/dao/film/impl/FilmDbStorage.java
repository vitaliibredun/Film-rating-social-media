package ru.yandex.practicum.filmorate.dao.film.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.film.FilmStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.*;
import java.sql.Date;
import java.util.*;

@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addFilm(Film film) {
        String sql = "insert into films (name, description, release_date, duration, mpa_id) " +
                "values (?, ?, ?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"id"});
            statement.setString(1, film.getName());
            statement.setString(2, film.getDescription());
            statement.setDate(3, Date.valueOf(film.getReleaseDate()));
            statement.setInt(4, film.getDuration());
            statement.setInt(5, film.getMpa().getId());
            return statement;

        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public Film updateFilm(Film film) {
        String sql = "update films set " +
                "name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? " +
                "where id = ?";

        jdbcTemplate.update(sql
                , film.getName()
                , film.getDescription()
                , film.getReleaseDate()
                , film.getDuration()
                , film.getMpa().getId()
                , film.getId());

        return film;
    }

    @Override
    public Collection<Film> findAllFilms() {
        String sql = "select f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id, " +
                "array_agg(g.name_genre) as genre_name, array_agg(g.id_genre) as genre_id, mpa.name_mpa " +
                "from films as f " +
                "left outer join film_genre as fg on f.id = fg.film_id " +
                "left outer join genre as g on fg.genre_id = g.id_genre " +
                "left outer join film_mpa as mpa on f.mpa_id = mpa.id " +
                "group by f.id";

        return jdbcTemplate.query(sql, this::mapRowToFilm);
    }

    @Override
    public Film findFilmById(Integer id) {
        String sql = "select f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id, " +
                "array_agg(g.name_genre) as genre_name, array_agg(g.id_genre) as genre_id, mpa.name_mpa " +
                "from films as f " +
                "left outer join film_genre as fg on f.id = fg.film_id " +
                "left outer join genre as g on fg.genre_id = g.id_genre " +
                "left outer join film_mpa as mpa on f.mpa_id = mpa.id "+
                "where f.id = ?";

        return jdbcTemplate.queryForObject(sql, this::mapRowToFilm, id);
    }

    @Override
    public void addRateToFilm(Integer filmId, Integer userId) {
        String sql = "insert into films_likes (film_id, likes_id) " +
                "values (?, ?)";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void deleteRateFromFilm(Integer filmId, Integer userId) {
        String sql = "delete from films_likes " +
                "where film_id = ? and likes_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public List<Film> findFilmsByRate(Integer count) {
        String sql = "select f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id, " +
                "array_agg(g.name_genre) as genre_name, array_agg(g.id_genre) as genre_id, mpa.name_mpa " +
                "from films as f " +
                "left outer join films_likes as fl on f.id = fl.film_id " +
                "left outer join film_genre as fg on f.id = fg.film_id " +
                "left outer join genre as g on fg.genre_id = g.id_genre " +
                "left outer join film_mpa as mpa on f.mpa_id = mpa.id " +
                "group by f.id " +
                "order by count(fl.likes_id) desc " +
                "limit ?";

        return jdbcTemplate.query(sql, this::mapRowToFilm, count);
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        LinkedHashSet<Genre> genres = new LinkedHashSet<>();
        String genreIds = Arrays.toString((Object[]) resultSet.getArray("genre_id").getArray());
        String genreNames = Arrays.toString((Object[]) resultSet.getArray("genre_name").getArray());
        if ((!genreIds.equals("[null]")) && (!genreNames.equals("[null]"))) {
            String[] id = genreIds.split("\\[|,|\\]| " );
            String[] name = genreNames.split("\\[|,|\\]| ");
            for (int i = 0; i < id.length; i++) {
                for (int j = 0; j < name.length; j++) {
                    if ((id[i].length() == 1) || (name[i].length() > 2)) {
                        Genre genre = Genre.builder().id(Integer.valueOf(id[i])).name(name[i]).build();
                        genres.add(genre);
                    }
                }
            }
        }

        return Film.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .mpa(Mpa.builder().id(resultSet.getInt("mpa_id")).name(resultSet.getString("name_mpa")).build())
                .genres(genres)
                .build();
    }
}
