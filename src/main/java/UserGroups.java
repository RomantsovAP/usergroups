import model.Group;
import model.Status;
import model.User;
import repository.GroupRepository;
import repository.JdbcGroupRepository;
import repository.JdbcUserRepository;
import repository.UserRepository;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;


public class UserGroups {
    public static void main(String[] args) {

        try {
            Properties dbproperties = new Properties();
            dbproperties.load(UserGroups.class.getResourceAsStream("/db/db.properties"));
            Connection connection = DriverManager.getConnection(
                    (String)dbproperties.get("url"),
                    (String)dbproperties.get("username"),
                    (String)dbproperties.get("password"));
            GroupRepository groupRepository = new JdbcGroupRepository(connection);
            List<Group> groups = groupRepository.getGroupsWithMoreThenTwoUsers();
            UserRepository userRepository = new JdbcUserRepository(connection);
            User user = new User("UserX", "UserX", Status.Active, LocalDate.of(1980, 8, 6),groups.get(0));
            userRepository.save(user);
            user.setName("UserXXX");
            userRepository.save(user);
            userRepository.delete(71);
            userRepository.setStatus(user, Status.Inactive);
            userRepository.getUsersByGroupAndStatus(groups.get(0),Status.Active).stream().forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
