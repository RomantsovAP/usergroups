package repository;

import model.Group;
import model.Status;
import model.User;

import java.util.List;

interface UserRepository {

    User save(User user);

    boolean delete (int id);

    boolean setStatus(Status status);

    boolean setGroup(Group group);

    List<User> getAllUsers();

    List<User> getUsersWithBirthDayLastMonthOrMonthBeforeLastMonth();

    List<User> getUsersByGroup(Group group);

    List<User> getUsersByStatus(Status status);

    List<User> getUsersByGroupAndStatus(Group group, Status status);
}
