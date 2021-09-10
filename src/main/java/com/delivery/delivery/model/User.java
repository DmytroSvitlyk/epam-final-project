package com.delivery.delivery.model;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String firstName;
    private String secondName;
    private String login;
    private String email;
	private String phone;
    private String password;
    private Role role;

    public User() {

    }

    public User(int id, String firstName, String secondName, String login, String email, String phone, String password, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.login = login;
        this.email = email;
		this.phone = phone;
        this.password = password;
        this.role = role;
    }

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
