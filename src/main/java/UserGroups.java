import model.Group;
import model.Status;
import model.User;
import repository.GroupRepository;
import repository.JdbcGroupRepository;
import repository.JdbcUserRepository;
import repository.UserRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;


public class UserGroups {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:C:/tt/embededBase","admin","");
            GroupRepository groupRepository = new JdbcGroupRepository(connection);
            //groupRepository.save(new Group(2, "222"));
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
        }
    }
}
