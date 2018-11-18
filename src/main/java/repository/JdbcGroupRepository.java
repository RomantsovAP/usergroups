package repository;

import model.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcGroupRepository implements GroupRepository {

    private Connection connection;

    public JdbcGroupRepository(Connection connection) {
        this.connection = connection;
    }

    public Group save(Group group) {
        try {
            String query;
            if (group.isNew()) {
                query = String.format("INSERT INTO groups (name) VALUES ('%s')", group.getName());
            } else {
                query = String.format("UPDATE groups SET NAME = '%s' WHERE ID = %s", group.getName(), group.getId());
            }
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (group.isNew() && resultSet.next()) {
                group.setId(resultSet.getInt("ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return group;
    }

    public boolean delete(int id) {
        boolean result = false;
        try {
            Statement statement = connection.createStatement();
            String query = String.format("DELETE FROM groups WHERE ID = %s", id);
            result = statement.executeUpdate(query) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Group> getGroupsWithMoreThenTwoUsers() {
        List<Group> result = new ArrayList<Group>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT ID, NAME FROM GROUPS WHERE (ID IN " +
                    "(SELECT GROUP_ID FROM USERS GROUP BY GROUP_ID HAVING COUNT(GROUP_ID) > 2)) ORDER BY  ID";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result.add(new Group(resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
