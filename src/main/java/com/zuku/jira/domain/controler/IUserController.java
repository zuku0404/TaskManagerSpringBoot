package com.zuku.jira.domain.controler;

import com.zuku.jira.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserController {
    public User getUser(Long userId);

    public List<User> getUsers();

    public ResponseEntity<Object> updateUser(String firstName, String lastName);

    public ResponseEntity<Object> updatePassword(String newPassword);

    public ResponseEntity<Object> removeUser();
}