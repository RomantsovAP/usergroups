import model.Group;
import model.Status;
import model.User;
import repository.GroupRepository;
import repository.JdbcGroupRepository;
import repository.JdbcUserRepository;
import repository.UserRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


public class UserGroups {

    private static Connection connection;

    private static void initDBConnection() {
        try {
            Properties dbproperties = new Properties();
            dbproperties.load(UserGroups.class.getResourceAsStream("/db/db.properties"));
            connection = DriverManager.getConnection(
                    (String)dbproperties.get("url"),
                    (String)dbproperties.get("username"),
                    (String)dbproperties.get("password"));

            BufferedReader reader = new BufferedReader(new InputStreamReader(UserGroups.class.getResourceAsStream("/db/populateDb.sql")));
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

    private static void printList(List list) {
        for (Object obj: list) {
            System.out.println(obj);
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        initDBConnection();

        GroupRepository groupRepository = new JdbcGroupRepository(connection);
        UserRepository userRepository = new JdbcUserRepository(connection);

        System.out.println("***users by group 1");
        printList(userRepository.getUsersByGroup(new Group(1, "Test")));
        System.out.println("***users by status Inactive");
        printList(userRepository.getUsersByStatus(Status.Inactive));
        System.out.println("***groups with more then two users");
        printList(groupRepository.getGroupsWithMoreThenTwoUsers());
        System.out.println("***users with birthday last month or month before last month");
        printList(userRepository.getUsersWithBirthDayLastMonthOrMonthBeforeLastMonth());
        System.out.println("***change status for user1 ->> Inactive");
        userRepository.setStatus(userRepository.getAllUsers().get(0), Status.Inactive);
        printList(userRepository.getAllUsers());
        System.out.println("***change group for user2 ->> group2");
        userRepository.setGroup(userRepository.getAllUsers().get(1), new Group(2,"tt"));
        printList(userRepository.getAllUsers());
        System.out.println("***delete first user");
        userRepository.delete(userRepository.getAllUsers().get(0).getId());
        printList(userRepository.getAllUsers());

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
