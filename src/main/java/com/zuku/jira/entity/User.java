package com.zuku.jira.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    @OneToOne(cascade = CascadeType.REMOVE)
    private Account account;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private Set<Task> tasks;

    public User() {
    }

    public User(String name, String lastName, Account account) {
        this.name = name;
        this.lastName = lastName;
        this.account = account;
    }

//    public User(Long id, String name, String lastName, Account account, Set<Task> tasks) {
//        this.id = id;
//        this.name = name;
//        this.lastName = lastName;
//        this.account = account;
//        this.tasks = tasks;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

//    public Set<Task> getTasks() {
//        return tasks;
//    }
//
//    public void setTasks(Set<Task> tasks) {
//        this.tasks = tasks;
//    }
}