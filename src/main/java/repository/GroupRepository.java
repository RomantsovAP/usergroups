package repository;

import model.Group;

import java.util.List;

public interface GroupRepository {
    Group save(Group group);

    boolean delete (int id);

    List<Group> getGroupsWithMoreThenTwoUsers();

    List<Group> getAllGroups();
}
