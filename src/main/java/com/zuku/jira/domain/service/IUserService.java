package com.zuku.jira.domain.service;

import com.zuku.jira.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {
    public ResponseEntity<Object> unregister();
    public ResponseEntity<Object> editUserData(String firstName, String lastName);
    public ResponseEntity<Object> editPassword(String newPassword);
    public User findUser(Long userId);
    public List<User> findAllUsers();
}
