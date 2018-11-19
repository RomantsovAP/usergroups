package repository;

import model.Group;
import model.Status;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JdbcUserRepositoryTest {

    private Connection connection;
    private UserRepository userRepository;

    @Before
    public void createRepositoryAndConnection() {
        try {
            Properties dbproperties = new Properties();
            dbproperties.load(JdbcGroupRepositoryTest.class.getResourceAsStream("/db/db.properties"));
            connection = DriverManager.getConnection(
                    (String)dbproperties.get("url"),
                    (String)dbproperties.get("username"),
                    (String)dbproperties.get("password"));
            userRepository = new JdbcUserRepository(connection);
            BufferedReader reader = new BufferedReader(new InputStreamReader(JdbcGroupRepositoryTest.class.getResourceAsStream("/db/populateDb.sql")));
            String script = reader.lines().collect(Collectors.joining("\n"));
            reader.close();
            Statement populatedb = connection.createStatement();
            populatedb.execute(script);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenAddSomeUserThenItWorks() {
        User newUser = userRepository.save(
                new User("UserX", "UserX", Status.Active,
                        LocalDate.of(1980, 8, 6),
                        new Group(1,"TestGroup")));
        assertThat(newUser.isNew(), is(false));
    }

    @Test
    public void whenModifySomeUserThenItWorks() {
        User newUser = userRepository.save(
                new User("UserX", "UserX", Status.Active,
                        LocalDate.of(1980, 8, 6),
                        new Group(1,"TestGroup")));
        newUser.setName("UserXXXL");
        User result = userRepository.save(newUser);
        assertThat(result.getName(), is("UserXXXL"));
    }

    @Test
    public void whenDeleteSomeUserThenItDeletes() {
        User newUser = userRepository.save(
                new User("UserX", "UserX", Status.Active,
                        LocalDate.of(1980, 8, 6),
                        new Group(1,"TestGroup")));
        assertThat(userRepository.delete(newUser.getId()), is(true));
        assertThat(userRepository.delete(newUser.getId()), is(false));
    }

    @Test
    public void whenChangeStatusThenItWorks() {
        List<User> users = userRepository.getAllUsers();
        userRepository.setStatus(users.get(0), Status.Inactive);
        users = userRepository.getAllUsers();
        assertThat(users.get(0).getStatus(), is(Status.Inactive));
    }

    @Test
    public void whenWeSetSomeGroupToUsersThenItWorks() {
        List<User> users = userRepository.getAllUsers();
        userRepository.setGroup(users.get(0), new Group(1, "Test"));
        users = userRepository.getAllUsers();
        assertThat(users.get(0).getGroup().getId(), is(1));
    }

    @Test
    public void getAllUsersReturnsAllUsers() {
        assertThat(userRepository.getAllUsers().size(), is(12));
    }

    @Test
    public void getUsersWithBirthDayLastMonthOrMonthBeforeLastMonth() {
        userRepository.save(
                new User("UserX", "UserX", Status.Active,
                        LocalDate.now().minusMonths(1),
                        new Group(1, "TestGroup")));
        List<User> users = userRepository.getAllUsers();
        int count = 0;
        for (User user : users) {
            if (user.getBirthday().getMonthValue() == LocalDate.now().getMonthValue() - 1 ||
                    user.getBirthday().getMonthValue() == LocalDate.now().getMonthValue() - 2) {
                count++;
            }
        }
        assertThat(userRepository.getUsersWithBirthDayLastMonthOrMonthBeforeLastMonth().size(), is(count));
    }

    @Test
    public void whenWeSeekForUsersByGroupThenItWorks() {
        List<User> users = userRepository.getAllUsers();
        assertThat(userRepository.getUsersByGroup(users.get(0).getGroup()).size(), is (3));
    }

    @Test
    public void whenWeSeekForUsersByStatusThenItWorks() {
        assertThat(userRepository.getUsersByStatus(Status.Inactive).size(), is (2));
    }

    @Test
    public void whenWeSeekForUsersByGroupAndStatusItWorks() {
        List<User> users = userRepository.getAllUsers();
        assertThat(userRepository.getUsersByGroupAndStatus(users.get(4).getGroup(), Status.Active).size(), is(3));
    }

}
