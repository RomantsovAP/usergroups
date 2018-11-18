package repository;

import model.Group;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JdbcGroupRepositoryTest {

    private Connection connection;
    private GroupRepository groupRepository;

    @Before
    public void createRepositoryAndConnection() {
        try {
            Properties dbproperties = new Properties();
            dbproperties.load(JdbcGroupRepositoryTest.class.getResourceAsStream("/db/db.properties"));
            connection = DriverManager.getConnection(
                    (String)dbproperties.get("url"),
                    (String)dbproperties.get("username"),
                    (String)dbproperties.get("password"));
            groupRepository = new JdbcGroupRepository(connection);
            BufferedReader reader = new BufferedReader(new InputStreamReader(JdbcGroupRepositoryTest.class.getResourceAsStream("/db/populateDb.sql")));
            String script = reader.lines().collect(Collectors.joining("\n"));
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
    public void whenAddSomeGroupItWorks() {
        Group newGroup = groupRepository.save(new Group("TestGroup1"));
        assertThat(newGroup.isNew(), is(false));
    }

    @Test
    public void whenAddSomeAndDeleteItItWorks() {
        Group newGroup = groupRepository.save(new Group("TestGroup1"));
        assertThat(groupRepository.delete(newGroup.getId()), is(true));
        assertThat(groupRepository.delete(newGroup.getId()), is(false));
    }

    @Test
    public void whenSeekForGroupsWithMoreThenTwoUsersItWorks() {
        List<Group> groups = groupRepository.getGroupsWithMoreThenTwoUsers();
        assertThat(groups.size(), is(3));
    }

}
