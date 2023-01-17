package ru.yandex.practicum.filmorate.dao.user.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.user.UserStorage;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component("userDbStorage")
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addUser(User user) {
        String sql = "insert into users (email, login, name, birthday) " +
                     "values (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"id"});
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getName());
            statement.setDate(4, Date.valueOf(user.getBirthday()));
            return statement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public User updateUser(User user) {
        String sql = "update users set " +
                     "email = ?, login = ?, name = ?, birthday = ? " +
                     "where id = ?";
        jdbcTemplate.update(sql
                , user.getEmail()
                , user.getLogin()
                , user.getName()
                , user.getBirthday()
                , user.getId());

        return user;
    }

    @Override
    public Collection<User> findAllUsers() {
        String sql = "select id, email, login, name, birthday " +
                     "from users";

        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    @Override
    public User findUserById(Integer id) {
        String sql = "select id, email, login, name, birthday " +
                     "from users " +
                     "where id = ?";

        List<User> query = jdbcTemplate.query(sql, this::mapRowToUser, id);
        if (query.isEmpty()) {
            throw new UserNotFoundException("The user with the id doesn't exists");
        } else {
            return query.get(0);
        }
    }

    @Override
    public void addFriend(Integer id, Integer friendId) {
        String sql = "insert into user_friend (user_id, friend_id, friendship) " +
                     "values (?, ?, ?)";

        boolean friendship = true;

        jdbcTemplate.update(sql, id, friendId, friendship);
    }

    @Override
    public void deleteFriend(Integer id,Integer friendId) {
        String sql = "delete from user_friend " +
                     "where user_id = ? and friend_id = ?";

        jdbcTemplate.update(sql, id, friendId);
    }

    @Override
    public List<Integer> findUserFriends(Integer id) {
        String sql = "select distinct friend_id " +
                     "from user_friend " +
                     "where user_id = ?";

        return jdbcTemplate.queryForList(sql, Integer.class, id);
    }

    @Override
    public List<Integer> findCommonFriends(Integer id, Integer otherId) {
        String sql = "select friend_id " +
                     "from user_friend " +
                     "where user_id = ? " +
                     "intersect " +
                     "select friend_id " +
                     "from user_friend " +
                     "where user_id = ?";

        return jdbcTemplate.queryForList(sql, Integer.class, id, otherId);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }
}
