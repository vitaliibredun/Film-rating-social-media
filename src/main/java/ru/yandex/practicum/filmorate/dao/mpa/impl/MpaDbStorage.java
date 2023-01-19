package ru.yandex.practicum.filmorate.dao.mpa.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component("mpaDbStorage")
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa findMpa(Integer id) {
        String sql = "select * " +
                     "from film_mpa " +
                     "where id = ?";

        return jdbcTemplate.queryForObject(sql, this::mapToMpa, id);
    }

    @Override
    public Collection<Mpa> findAllMpa() {
        String sql = "select * " +
                     "from film_mpa ";

        return jdbcTemplate.query(sql, this::mapToMpa);
    }

    private Mpa mapToMpa(ResultSet resultSet, int rowNum) throws SQLException {
        return Mpa.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name_mpa"))
                .build();
    }
}
