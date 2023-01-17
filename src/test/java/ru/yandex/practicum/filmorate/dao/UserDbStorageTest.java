package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.dao.user.UserStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;


@SpringBootTest
@AutoConfigureTestDatabase
class UserDbStorageTest {
	@Qualifier("userDbStorage")
	@Autowired
	private UserStorage userStorage;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void cleanUp() {
		jdbcTemplate.execute("DELETE FROM users");
		jdbcTemplate.execute("ALTER TABLE users ALTER COLUMN id RESTART WITH 1");
	}

	@Test
	@DisplayName("Проверка добавления пользователя")
	void addUserTest() {
		User userExpected = User.builder()
				.id(1)
				.login("stark")
				.name("Tony")
				.email("tony@gmail.com")
				.birthday(LocalDate.of(1965, 4, 4))
				.build();

		User user = User.builder()
				.login("stark")
				.name("Tony")
				.email("tony@gmail.com")
				.birthday(LocalDate.of(1965, 4, 4))
				.build();

		userStorage.addUser(user);

		User userFromDb = userStorage.findUserById(1);

		Assertions.assertEquals(userExpected, userFromDb, "Данные не совпадают");
	}

	@Test
	@DisplayName("Проверка обновления пользователя")
	void updateUserTest() {
		User userExpected = User.builder()
				.id(1)
				.login("stark")
				.name("Tony")
				.email("tony@gmail.com")
				.birthday(LocalDate.of(1965, 4, 4))
				.build();

		User user = User.builder()
				.login("parker")
				.name("Peter")
				.email("peter@gmail.com")
				.birthday(LocalDate.of(1996, 6, 1))
				.build();

		userStorage.addUser(user);

		userStorage.updateUser(userExpected);

		User userFromDb = userStorage.findUserById(1);

		Assertions.assertEquals(userExpected, userFromDb, "Данные не совпадают");
	}

	@Test
	@DisplayName("Проверка вызова всех пользователей")
	void findAllUsersTest() {
		Assertions.assertEquals(0, userStorage.findAllUsers().size());
		User userExpected1 = User.builder()
				.id(1)
				.login("stark")
				.name("Tony")
				.email("tony@gmail.com")
				.birthday(LocalDate.of(1965, 4, 4))
				.build();

		User userExpected2 = User.builder()
				.id(2)
				.login("parker")
				.name("Peter")
				.email("peter@gmail.com")
				.birthday(LocalDate.of(1996, 6, 1))
				.build();

		User user1 = User.builder()
				.login("stark")
				.name("Tony")
				.email("tony@gmail.com")
				.birthday(LocalDate.of(1965, 4, 4))
				.build();

		User user2 = User.builder()
				.login("parker")
				.name("Peter")
				.email("peter@gmail.com")
				.birthday(LocalDate.of(1996, 6, 1))
				.build();

		userStorage.addUser(user1);
		userStorage.addUser(user2);

		User userFromDb1 = userStorage.findUserById(1);
		User userFromDb2 = userStorage.findUserById(2);

		Assertions.assertAll(
				() -> Assertions.assertEquals(2, userStorage.findAllUsers().size(), "Данные не верны"),
				() -> Assertions.assertEquals(userExpected1, userFromDb1, "Данные не совпадают"),
				() -> Assertions.assertEquals(userExpected2, userFromDb2, "Данные не совпадают")
		);
	}

	@Test
	@DisplayName("Проверка вызова пользователя по id")
	void findUserByIdTest() {
		Assertions.assertEquals(0, userStorage.findAllUsers().size(), "Данные не верны");

		User userExpected = User.builder()
				.id(1)
				.login("stark")
				.name("Tony")
				.email("tony@gmail.com")
				.birthday(LocalDate.of(1965, 4, 4))
				.build();

		User user = User.builder()
				.login("stark")
				.name("Tony")
				.email("tony@gmail.com")
				.birthday(LocalDate.of(1965, 4, 4))
				.build();

		userStorage.addUser(user);

		User userFromDb = userStorage.findUserById(1);

		Assertions.assertEquals(userExpected, userFromDb, "Данные не совпадают");
	}

	@Test
	@DisplayName("Проверка добавления пользователя в друзья")
	void addFriendTest() {
		User user1 = User.builder()
				.id(1)
				.login("stark")
				.name("Tony")
				.email("tony@gmail.com")
				.birthday(LocalDate.of(1965, 4, 4))
				.build();

		User user2 = User.builder()
				.id(2)
				.login("parker")
				.name("Peter")
				.email("peter@gmail.com")
				.birthday(LocalDate.of(1996, 6, 1))
				.build();
		userStorage.addUser(user1);
		userStorage.addUser(user2);

		Assertions.assertAll(
				() -> Assertions.assertEquals(0, userStorage.findUserFriends(user1.getId()).size(), "Данные не верны"),
				() -> Assertions.assertEquals(0, userStorage.findUserFriends(user2.getId()).size(), "Данные не верны")
		);

		userStorage.addFriend(user2.getId(), user1.getId());

		Assertions.assertAll(
				() -> Assertions.assertEquals(0, userStorage.findUserFriends(user1.getId()).size(), "Данные не верны"),
				() -> Assertions.assertEquals(1, userStorage.findUserFriends(user2.getId()).size(), "Данные не верны")
		);
	}

	@Test
	@DisplayName("Проверка удаления пользователя из друзей")
	void deleteFriendTest() {
		User user1 = User.builder()
				.id(1)
				.login("stark")
				.name("Tony")
				.email("tony@gmail.com")
				.birthday(LocalDate.of(1965, 4, 4))
				.build();

		User user2 = User.builder()
				.id(2)
				.login("parker")
				.name("Peter")
				.email("peter@gmail.com")
				.birthday(LocalDate.of(1996, 6, 1))
				.build();
		userStorage.addUser(user1);
		userStorage.addUser(user2);
		userStorage.addFriend(user2.getId(), user1.getId());

		Assertions.assertAll(
				() -> Assertions.assertEquals(0, userStorage.findUserFriends(user1.getId()).size(), "Данные не верны"),
				() -> Assertions.assertEquals(1, userStorage.findUserFriends(user2.getId()).size(), "Данные не верны")
		);

		userStorage.deleteFriend(user2.getId(), user1.getId());

		Assertions.assertAll(
				() -> Assertions.assertEquals(0, userStorage.findUserFriends(user1.getId()).size(), "Данные не верны"),
				() -> Assertions.assertEquals(0, userStorage.findUserFriends(user2.getId()).size(), "Данные не верны")
		);
	}

	@Test
	@DisplayName("Проверка поиска друзей пользователя")
	void findUserFriendsTest() {
		User user1 = User.builder()
				.id(1)
				.login("stark")
				.name("Tony")
				.email("tony@gmail.com")
				.birthday(LocalDate.of(1965, 4, 4))
				.build();

		User user2 = User.builder()
				.id(2)
				.login("parker")
				.name("Peter")
				.email("peter@gmail.com")
				.birthday(LocalDate.of(1996, 6, 1))
				.build();
		userStorage.addUser(user1);
		userStorage.addUser(user2);

		Assertions.assertAll(
				() -> Assertions.assertEquals(0, userStorage.findUserFriends(user1.getId()).size(), "Данные не верны"),
				() -> Assertions.assertEquals(0, userStorage.findUserFriends(user2.getId()).size(), "Данные не верны")
		);

		userStorage.addFriend(user2.getId(), user1.getId());

		List<Integer> userFriends = userStorage.findUserFriends(user2.getId());

		Assertions.assertEquals(user1, userStorage.findUserById(userFriends.get(0)), "Данные не верны");
	}

	@Test
	@DisplayName("Проверка поиска общих друзей между двумя пользователями")
	void findCommonFriendsTest() {
		User user1 = User.builder()
				.id(1)
				.login("stark")
				.name("Tony")
				.email("tony@gmail.com")
				.birthday(LocalDate.of(1965, 4, 4))
				.build();

		User user2 = User.builder()
				.id(2)
				.login("parker")
				.name("Peter")
				.email("peter@gmail.com")
				.birthday(LocalDate.of(1996, 6, 1))
				.build();

		User user3 = User.builder()
				.id(3)
				.login("bruce")
				.name("Bruce")
				.email("bruce@gmail.com")
				.birthday(LocalDate.of(1967, 11, 22))
				.build();
		userStorage.addUser(user1);
		userStorage.addUser(user2);
		userStorage.addUser(user3);
		userStorage.addFriend(user2.getId(), user1.getId());
		userStorage.addFriend(user3.getId(), user1.getId());

		List<Integer> commonFriends = userStorage.findCommonFriends(user2.getId(), user3.getId());

		Assertions.assertEquals(user1, userStorage.findUserById(commonFriends.get(0)), "Данные не верны");
	}
}
