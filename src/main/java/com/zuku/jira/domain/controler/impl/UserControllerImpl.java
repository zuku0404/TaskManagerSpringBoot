package com.zuku.jira.domain.controler.impl;

import com.zuku.jira.domain.controler.IUserController;
import com.zuku.jira.domain.service.impl.UserServiceImpl;
import com.zuku.jira.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserControllerImpl implements IUserController {
    private final UserServiceImpl userService;

    @GetMapping(path = "/{userId}")
    @Override
    public User getUser(@PathVariable("userId") Long userId) {
        return userService.findUser(userId);
    }

    @GetMapping
    @Override
    public List<User> getUsers() {
        return userService.findAllUsers();
    }

    @PutMapping(path = "/me")
    @Override
    public ResponseEntity<Object> updateUser(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        return userService.editUserData(firstName, lastName);
    }

    @PutMapping(path = "/password")
    @Override
    public ResponseEntity<Object> updatePassword(
            @RequestParam String newPassword) {
        return userService.editPassword(newPassword);
    }

    @DeleteMapping(path = "/me")
    @Override
    public ResponseEntity<Object> removeUser() {
        return userService.unregister();
    }
}

