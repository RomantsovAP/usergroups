package repository;

import model.Group;
import model.Status;
import model.User;

import java.util.List;

public interface UserRepository {

    User save(User user);

    boolean delete (int id);

    boolean setStatus(User user, Status status);

    boolean setGroup(User user, Group group);

    List<User> getAllUsers();

    List<User> getUsersWithBirthDayLastMonthOrMonthBeforeLastMonth();

    List<User> getUsersByGroup(Group group);

    List<User> getUsersByStatus(Status status);

    List<User> getUsersByGroupAndStatus(Group group, Status status);
}
