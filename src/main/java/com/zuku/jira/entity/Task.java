package com.zuku.jira.entity;

import jakarta.persistence.*;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String title;
    private String description;

    @ManyToOne
    private Board board;

    @ManyToOne
    private User user;

    public Task(Long id, String title, String description, User user, Board board) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.user = user;
        this.board = board;
    }

    public Task() {
    }

    public Task(String title, String description, User user, Board board) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.board = board;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
