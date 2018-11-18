package repository;

import model.Group;
import model.Status;
import model.User;
import util.DateTimeUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserRepository implements UserRepository {

    private Connection connection;

    public JdbcUserRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User save(User user) {
        if (user.getGroup() == null) {
            throw  new IllegalArgumentException("Can't save user without group!");
        }
        try {
            String query;
            if (user.isNew()) {
                query = String.format("INSERT INTO users (name, login, birthday, status, group_id) VALUES ('%s','%s','%s', '%s', %d)",
                        user.getName(), user.getLogin(), user.getBirthday(), user.getStatus(), user.getGroup().getId());
            } else {
                query = String.format("UPDATE users SET NAME = '%s', LOGIN = '%s', BIRTHDAY = '%s', STATUS = '%s', GROUP_ID = '%s'  WHERE ID = %s",
                        user.getName(), user.getLogin(), user.getBirthday(), user.getStatus(), user.getGroup().getId(), user.getId());
            }
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (user.isNew() && resultSet.next()) {
                user.setId(resultSet.getInt("ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        try {
            Statement statement = connection.createStatement();
            String query = String.format("DELETE FROM users WHERE ID = %s", id);
            result = statement.executeUpdate(query) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean setStatus(User user, Status status) {
        user.setStatus(status);
        save(user);
        return false;
    }

    @Override
    public boolean setGroup(User user, Group group) {
        user.setGroup(group);
        return false;
    }

    private User getUserFromQueryResult(ResultSet resultSet) {
        User newUser = null;
        try {
            newUser = new User(resultSet.getInt("ID"),
                    resultSet.getString("USERNAME"),
                    resultSet.getString("LOGIN"),
                    Status.valueOf(resultSet.getString("STATUS")),
                    resultSet.getDate("BIRTHDAY").toLocalDate(),
                    new Group(resultSet.getInt("GROUP_ID"), resultSet.getString("GROUPNAME")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newUser;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<User>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT USERS.ID AS ID, USERS.NAME AS USERNAME, LOGIN, BIRTHDAY, STATUS, GROUP_ID, GROUPS.NAME AS GROUPNAME " +
                    "FROM USERS LEFT JOIN GROUPS ON GROUP_ID = GROUPS.ID ORDER BY USERS.ID";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result.add(getUserFromQueryResult(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<User> getUsersWithBirthDayLastMonthOrMonthBeforeLastMonth() {
        List<User> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query =  "SELECT USERS.ID AS ID, USERS.NAME AS USERNAME, LOGIN, BIRTHDAY, STATUS, GROUP_ID, GROUPS.NAME AS GROUPNAME\n" +
                            "FROM USERS LEFT JOIN GROUPS ON GROUP_ID = GROUPS.ID\n" +
                            "WHERE MONTH(BIRTHDAY) = MONTH(DATEADD('MONTH', -1 , NOW())) OR MONTH(BIRTHDAY) = MONTH(DATEADD('MONTH', -2 , NOW()))\n" +
                            "ORDER BY USERS.ID";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result.add(getUserFromQueryResult(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<User> getUsersByGroup(Group group) {
        List<User> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query =  String.format("SELECT USERS.ID AS ID, USERS.NAME AS USERNAME, LOGIN, BIRTHDAY, STATUS, GROUP_ID, GROUPS.NAME AS GROUPNAME\n" +
                    "FROM USERS LEFT JOIN GROUPS ON GROUP_ID = GROUPS.ID\n" +
                    "WHERE GROUP_ID = %d \n" +
                    "ORDER BY USERS.ID", group.getId());
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result.add(getUserFromQueryResult(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<User> getUsersByStatus(Status status) {
        List<User> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query =  String.format("SELECT USERS.ID AS ID, USERS.NAME AS USERNAME, LOGIN, BIRTHDAY, STATUS, GROUP_ID, GROUPS.NAME AS GROUPNAME\n" +
                    "FROM USERS LEFT JOIN GROUPS ON GROUP_ID = GROUPS.ID\n" +
                    "WHERE STATUS = '%s' \n" +
                    "ORDER BY USERS.ID", status);
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result.add(getUserFromQueryResult(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<User> getUsersByGroupAndStatus(Group group, Status status) {
        List<User> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query =  String.format("SELECT USERS.ID AS ID, USERS.NAME AS USERNAME, LOGIN, BIRTHDAY, STATUS, GROUP_ID, GROUPS.NAME AS GROUPNAME\n" +
                    "FROM USERS LEFT JOIN GROUPS ON GROUP_ID = GROUPS.ID\n" +
                    "WHERE STATUS = '%s' AND GROUP_ID =%d \n" +
                    "ORDER BY USERS.ID", status, group.getId());
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result.add(getUserFromQueryResult(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
