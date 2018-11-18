package model;

import util.DateTimeUtil;

import java.time.LocalDate;

public class User extends AbstractBaseEntity {
    private String login;
    private Status status;
    private LocalDate birthday;
    private Group group;

    public User(String name, String login, Status status, LocalDate birthday, Group group) {
        this.name = name;
        this.login = login;
        this.status = status;
        this.birthday = birthday;
        this.group = group;
    }

    public User(int id, String name, String login, Status status, LocalDate birthday, Group group) {
        this(name, login, status, birthday, group);
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", status=" + status +
                ", birthday=" + DateTimeUtil.toString(birthday) +
                ", group=" + group +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
