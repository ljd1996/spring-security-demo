package com.example.springsecuritydemo2.domain;

import java.util.Date;

public class User {
    private Integer id;

    private String name;

    private String password;

    private Date lastPasswordChange;

    private Boolean enable;

    public User(Integer id, String name, String password, Date lastPasswordChange, Boolean enable) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.lastPasswordChange = lastPasswordChange;
        this.enable = enable;
    }

    public User(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.lastPasswordChange = user.getLastPasswordChange();
        this.enable = user.getEnable();
    }

    public User() {

    }

    @Override
    public String toString() {
        return "name:" + name + " password:" + password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }

    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}