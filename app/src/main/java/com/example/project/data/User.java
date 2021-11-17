package com.example.project.data;

public class User {
    public String firstName, lastName, email, password, username;

    public int age;
    public int weight;
    public int height;

    public User() {

    }

    public User(String firstName, String lastName, String email, String password, String username, int age, int weight, int height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    public User(String firstName, String lastName, String email, String password, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
    }
}
