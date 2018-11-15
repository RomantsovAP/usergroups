package model;

import util.DateTimeUtil;

import java.time.LocalDate;

public class User extends AbstractBaseEntity {
    private String login;
    private Status status;
    private LocalDate birthday;
    private Group group;

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
